package fiddle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayFiddle {
  private static final Logger log = LoggerFactory.getLogger(ArrayFiddle.class);

  public static void main(String[] args) {
    var arr =
        new String[][] {
          {},
        };
    for (String[] strings : arr) {
      updateCol2(strings);
    }
    log.info("{}", (Object) arr);
  }

  private static void updateCol2(String[] arr) {
    arr[1] = "Test";
  }
}
