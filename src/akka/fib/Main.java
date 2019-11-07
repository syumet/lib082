package akka.fib;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a positive integer:");
        int nth = scan.nextInt();
        scan.close();

        final ActorSystem system = ActorSystem.create("fib-number");
        final ActorRef master = system.actorOf(
                MasterActor.props(nth), "fib-number-master");

        master.tell(new MasterMessage.Start(), ActorRef.noSender());
    }
}
