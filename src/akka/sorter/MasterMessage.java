package akka.sorter;

/**
 * Messages that a Client Actor can send.
 */

public class MasterMessage {
	
    static public class Start {
        public Start() {
        }
    }
    
    static public class CurrentJob {
        public final int x;
        public CurrentJob(int n) {
            this.x = n;
        }
    }
}
