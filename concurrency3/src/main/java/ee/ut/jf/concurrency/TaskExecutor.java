package ee.ut.jf.concurrency;

import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor extends Thread {
  private final ExecutorService executorService = Executors.newFixedThreadPool(10);
  private final ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
  private final AtomicInteger tasks = new AtomicInteger(0);

  private volatile boolean stopped = false;

  public TaskExecutor() {
    super();
    start();
  }

  @Override
  public void run() {
    while (!stopped || tasks.get() > 0) {
      if (tasks.get() > 0) {
        process();
        tasks.decrementAndGet();
      }
    }
    executorService.shutdown();
  }

  private void process() {
    try {
      System.out.println("Downloaded " + completionService.take().get());
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    catch (ExecutionException e) {
      System.out.println("ERROR! " + e.getMessage());
    }
  }

  public void shutdown() {
    stopped = true;
  }

  public void submit(URL file) {
    completionService.submit(new Task(file));
    tasks.incrementAndGet();
  }
}
