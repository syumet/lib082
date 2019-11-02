package akka.fib;
/**
 * Messages that a Client Actor can send.
 */

public class MasterMessage {
	
    static public class Start {
        public Start() {
        }
    }
    
    static public class CurrentJob {
        public final int nth;
        public CurrentJob(int n) {
            this.nth = n;
        }
    }
}
