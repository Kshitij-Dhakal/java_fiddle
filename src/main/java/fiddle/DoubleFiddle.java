package fiddle;

public class DoubleFiddle {

  public static final String NOT_NUMBER = "[^\\d.-]";

  public static void main(String[] args) {
    System.out.println(Double.parseDouble("$50.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("-$50.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("+$50.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("+$500.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("-$500.00".replaceAll(NOT_NUMBER, "")));
    System.out.printf("%.0f%n", 123.45);
  }
}
