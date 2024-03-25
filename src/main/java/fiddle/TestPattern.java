package fiddle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
  public static void main(String[] args) {
    var str = "";
    String regex = "complete the process:[\\s]*(\\d+)[\\s]*If you did not attempt";
    Pattern pattern = Pattern.compile(regex);
    System.out.println(regex);
    Matcher matcher = pattern.matcher(str);
    while (matcher.find()) {
      String number = matcher.group(1);
      System.out.println("Found number: " + number);
    }
  }
}
