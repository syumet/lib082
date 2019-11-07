package akka.sorter;

import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkerActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final int workerId;	
    
    private ActorRef parent;		
    private final ActorRef childWorker;	
    
    private Integer curNum;		// current number I'm holding
    

    public WorkerActor(int workerId) {
        this.workerId = workerId;
        this.curNum = null;
    	childWorker = this.getContext().getSystem().actorOf(
    			WorkerActor.props(workerId + 1), "list-sorter-" + (workerId + 1));
    }
    
    
    static public Props props(int workerId) {
        return Props.create(WorkerActor.class, () -> new WorkerActor(workerId));
    }


    /**
     * Occurs on startup, before we are sent a message.
     */
    public void preStart() {
        //log.info("Fibonacci number worker actor ID {} created!", workerId);
    }

    
    /** 
     * Matches the received message to the corresponding method.
     */
    public Receive createReceive() {
        return receiveBuilder()
        .match(MasterMessage.CurrentJob.class, msg -> {
        	this.currentJob(msg);
        })
        .match(WorkerMessage.Finish.class, msg -> {
        	this.subListSorted(msg);
        })
        .build();
    }

    
    /**
     * Called if createReceive is sent the CurrentJob Message.
     * @param msg
     */
    private void currentJob(MasterMessage.CurrentJob msg){
    	
    	parent = getSender();
       
        // Checks if my job is just the base case 
        if (curNum == null) {
        	if (msg.x == 0) {
            	parent.tell(new WorkerMessage.Finish(
            			new ArrayList<>(Arrays.asList(0))), ActorRef.noSender());
        	} else {
        		curNum = msg.x;
        	}
        } else {
        	int toChild;
        	if (msg.x > curNum) {
        		toChild = curNum;
        		curNum = msg.x;
        	} else {
				toChild = msg.x;
			}       	
        	// send the larger number to child worker 
        	childWorker.tell(new MasterMessage.CurrentJob(toChild), this.getSelf()); 
        }
    } 
    
    /**
     * If my worker replies that it has sorted the sub-list, tell my boss.
     * @param msg
     */
    public void subListSorted(WorkerMessage.Finish msg) {
    	log.info("{}", msg.sortedList);
    	msg.sortedList.add(curNum);
        parent.tell(new WorkerMessage.Finish(msg.sortedList), ActorRef.noSender());          
    }

}
