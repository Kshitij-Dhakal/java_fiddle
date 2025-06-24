package fiddle.all;

import java.util.concurrent.ThreadLocalRandom;

public class RandomCustomerIdGenerator {
  public static void main(String[] args) {
    System.out.println(new RandomCustomerIdGenerator().getRandomCustomerId());
  }

  public String getRandomCustomerId() {
    return String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999));
  }
}
