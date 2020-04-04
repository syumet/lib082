package akka.sorter;

import java.util.ArrayList;
import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

    public static void main(String[] args) {
   
		// Get input from console
		System.out.print("Enter a list end with 0: ");
		Scanner input = new Scanner(System.in);
		String[] sp = input.nextLine().split(" ");
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(String s:sp) {
			l.add(Integer.parseInt(s));
		}
		//System.out.print(l.toString());      
		input.close();
		
		final ActorSystem system = ActorSystem.create("list-sorter");
		final ActorRef master = system.actorOf(MasterActor.props(l), "list-sorter-master");
		master.tell(new MasterMessage.Start(), ActorRef.noSender());
    }
}



