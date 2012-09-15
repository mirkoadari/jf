package ee.ut.jf.concurrency;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int i = queue.take();
                double sqrt = Math.sqrt(i);
                System.out.println("Consumed: " + i + " -> " + sqrt);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
