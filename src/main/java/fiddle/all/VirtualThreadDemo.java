package fiddle.all;

public class VirtualThreadDemo {
  public static void main(String[] args) throws InterruptedException {

    long start = System.currentTimeMillis();

    int count = 10_000; // Try 1 million if you're brave

    Thread[] threads = new Thread[count];

    for (int i = 0; i < count; i++) {
      threads[i] =
          Thread.ofVirtual()
              .start(
                  () -> {
                    try {
                      Thread.sleep(100); // Sleep doesn't block the OS thread!
                    } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                    }
                  });
    }

    for (Thread thread : threads) {
      thread.join();
    }

    long end = System.currentTimeMillis();
    System.out.println("Done in " + (end - start) + "ms");
  }
}
