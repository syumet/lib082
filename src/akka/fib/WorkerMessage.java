package akka.fib;
/**
 * Messages that only a Worker Actor can send.
 */

public class WorkerMessage {
    
    static public class Found {
        public final int i;
        public Found(int i) {
            this.i = i;
        }
    }
}
