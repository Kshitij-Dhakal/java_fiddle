package fiddle;

import java.util.concurrent.ThreadLocalRandom;

public class Application {
  public static void main(String[] args) {
    System.out.println(getRandomCustomerId());
    System.out.printf("%.2f", 100d);
  }

  protected static String getRandomCustomerId() {
    return String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999));
  }
}
