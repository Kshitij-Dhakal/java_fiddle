package fiddle.all;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VirtualThreadDemo {
  public static void main(String[] args) throws InterruptedException {

    long start = System.currentTimeMillis();

    int count = 10_000; // Try 1 million if you're brave

    Thread[] threads = new Thread[count];

    for (int i = 0; i < count; i++) {
      int finalI = i;
      threads[i] =
          Thread.ofVirtual()
              .start(
                  () -> {
                    try {
                      Thread.sleep(100); // Sleep doesn't block the OS thread!
                      log.info("Done waiting for {}", finalI);
                    } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                    }
                  });
    }

    for (Thread thread : threads) {
      thread.join();
    }

    long end = System.currentTimeMillis();
    log.info("Done in {} ms", (end - start));
  }
}
