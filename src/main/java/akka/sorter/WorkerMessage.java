package akka.sorter;

import java.util.ArrayList;

/**
 * Messages that only a Worker Actor can send.
 */

public class WorkerMessage {
    
    static public class Finish {
        public ArrayList<Integer> sortedList;
        public Finish(ArrayList<Integer> l) {
            this.sortedList = l;
        }
    }
}
