package fiddle.cheat.engine;

public class MemoryPrinter {
  public static volatile int money = 100;

  public static void main(String[] args) {
    while (true) {
      System.out.println("Money: " + money);
      try {
        Thread.sleep(1000); // Wait 1 second
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
