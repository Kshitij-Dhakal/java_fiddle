package fiddle;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtilsFiddle {
  public static void main(String[] args) {
    System.out.println(
        RandomStringUtils.random(10, 0, 0, true, false, null, ThreadLocalRandom.current()));
    System.out.println(
        RandomStringUtils.random(11, 0, 0, true, false, null, ThreadLocalRandom.current()));
    System.out.println(
        RandomStringUtils.random(12, 0, 0, true, false, null, ThreadLocalRandom.current()));
    System.out.println(
        RandomStringUtils.random(13, 0, 0, true, false, null, ThreadLocalRandom.current()));
  }
}
