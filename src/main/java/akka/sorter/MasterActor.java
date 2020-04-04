package akka.sorter;

import java.util.ArrayList;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MasterActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    
    // Instance variables needed
    private final ArrayList<Integer> listToSort;  
    private ActorRef firstWorker;		// The first worker actor we hired.
    private long tick;					// Time.
    
    public MasterActor(ArrayList<Integer> l) {
    	if (l.get(l.size() - 1) != 0) {
    		throw new IllegalArgumentException("missing 0");
    	}
        this.listToSort = l;
    }
    
    static public Props props(ArrayList<Integer> l) {
        return Props.create(MasterActor.class, () -> new MasterActor(l));
    }

    /**
     * Matches the received message to the corresponding method.
     */
	public Receive createReceive() {
        return receiveBuilder()
            .match(MasterMessage.Start.class, msg -> {
            	this.receiveStart(msg);
            })
            .match(WorkerMessage.Finish.class, msg -> {
            	this.fibFound(msg);
            })
            .build();
    }
    
    /**
     * Begins the worker actors if given the message to start.
     * @param msg
     */
    public void receiveStart(MasterMessage.Start msg){
        log.info("System Started!");
        tick = System.currentTimeMillis();	// timer
        firstWorker = this.getContext().getSystem().actorOf(
        		WorkerActor.props(1), "list-sorter-" + 1);
        for (Integer num : listToSort) {
            firstWorker.tell(new MasterMessage.CurrentJob(num), this.getSelf());
        }
    }
    
    /**
     * If a worker replies that it has sorted the list, terminate the system.
     * @param msg
     */
    public void fibFound(WorkerMessage.Finish msg) {   
        log.info("{}: (Time: {} ms)", msg.sortedList, System.currentTimeMillis() - tick);
    	this.getContext().getSystem().terminate();
    }

}
