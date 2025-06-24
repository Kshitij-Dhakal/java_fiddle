package fiddle.all;

import java.util.UUID;

public class UuidFiddle {
  public static void main(String[] args) {
    System.out.println(UUID.randomUUID().toString().replaceAll("[^a-zA-Z]", ""));
  }
}
