package fiddle;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFiddleApplication {

  public static void main(String[] args) {
    String output = Stream.<String>of().collect(Collectors.joining(" - "));
    System.out.println(output);
  }
}
