package fiddle.all;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayFiddle {
  private static final Logger log = LoggerFactory.getLogger(ArrayFiddle.class);

  public static void main(String[] args) {
    System.out.println(new String[2].length);
  }

  private static List<List<String>> arrayToList(String[][] expectedValue) {
    return Arrays.stream(expectedValue).map(Arrays::asList).collect(Collectors.toList());
  }

  private static void updateCol2(String[] arr) {
    arr[1] = "Test";
  }
}
