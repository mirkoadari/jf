package ee.ut.jf.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();

        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

        for (int i = 1; i <= 5; i++) {
            spawn(new Producer(queue), "producer-" + i);
        }

        for (int i = 1; i <= 7; i++) {
            spawn(new Consumer(queue), "consumer-" + i);
        }

        Thread.sleep(5000 - (System.currentTimeMillis() - start));
    }

    public static void spawn(Runnable r, String name) {
        Thread t = new Thread(r, name);
        t.setDaemon(true);
        t.start();
    }
}
