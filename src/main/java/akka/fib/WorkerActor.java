package akka.fib;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.fib.MasterMessage.CurrentJob;

public class WorkerActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final int workerId;	
    
    private ActorRef parent;		
    private ActorRef childWorker1;	
    private ActorRef childWorker2;
    
    private int childFib;	// the hold the result returned by my first child

    public WorkerActor(int workerId) {
        this.workerId = workerId;
        this.childFib = -1;
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
        .match(CurrentJob.class, msg -> {
        	this.currentJob(msg);
        })
        .match(WorkerMessage.Found.class, msg -> {
        	this.fibFound(msg);
        })
        .build();
    }

    
    /**
     * Called if createReceive is sent the CurrentJob Message.
     * @param msg
     */
    private void currentJob(CurrentJob msg){
    	
    	parent = getSender();
        //log.info("Fibonacci number worker actor ID {} started to compute {}", workerId, msg.nth);
       
        // Checks if my job is just the base case 
        if (msg.nth == 0 || msg.nth == 1) {
        	parent.tell(new WorkerMessage.Found(msg.nth), ActorRef.noSender());
        } else {
        	childWorker1 = this.getContext().getSystem().actorOf(
        			WorkerActor.props(2*workerId + 1), "fib-number-worker-" + (2*workerId + 1));
        	childWorker2 = this.getContext().getSystem().actorOf(
        			WorkerActor.props(2*workerId + 2), "fib-number-worker-" + (2*workerId + 2));
        	
        	childWorker1.tell(new CurrentJob(msg.nth - 1), this.getSelf());
        	childWorker2.tell(new CurrentJob(msg.nth - 2), this.getSelf());
        }

    }
    
    /**
     * If a worker replies that they have found a Perfect Number, record the number.
     * @param msg
     */
    public void fibFound(WorkerMessage.Found msg) {
        if (childFib == -1) {
        	childFib = msg.i;
        } else {
            // Replies to the sender the results of our calculations.
        	log.info("Computed {}", childFib + msg.i);
            parent.tell(new WorkerMessage.Found(childFib + msg.i), ActorRef.noSender());          
        }
    }

}
