package akka.fib;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

    public static void main(String[] args) {

        // Read in command line arguments
        int nthFibonacci = Integer.parseInt(args[0]);

        final ActorSystem system = ActorSystem.create("fib-number");
        final ActorRef master = system.actorOf(
                MasterActor.props(nthFibonacci), "fib-number-master");

        master.tell(new MasterMessage.Start(), ActorRef.noSender());
    }
}



