package akka.fib;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MasterActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    
    // Instance variables needed
    private final int fibToFind;		// Nth fib number we need to find.   
    private ActorRef firstWorker;		// The first worker actor we hired.
    private long tick;					// Time.
    
    public MasterActor(int fibToFind) {
    	if (fibToFind < 0) {
    		throw new IllegalArgumentException("n should be positive.");
    	}
        this.fibToFind = fibToFind;
    }
    
    static public Props props(int fibToFind) {
        return Props.create(MasterActor.class, () -> new MasterActor(fibToFind));
    }

    /**
     * Matches the received message to the corresponding method.
     */
	public Receive createReceive() {
        return receiveBuilder()
            .match(MasterMessage.Start.class, msg -> {
            	this.receiveStart(msg);
            })
            .match(WorkerMessage.Found.class, msg -> {
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
        firstWorker = this.getContext().getSystem().actorOf(WorkerActor.props(1), 
        		"fib-number-worker-" + 1);
        firstWorker.tell(new MasterMessage.CurrentJob(fibToFind), this.getSelf());
    }
    
    /**
     * If a worker replies that they have found the target number, record the number.
     * @param msg
     */
    public void fibFound(WorkerMessage.Found msg) {   
        log.info("Computed {}: (Time: {} ms)", msg.i, System.currentTimeMillis() - tick);
    	this.getContext().getSystem().terminate();
    }

}
