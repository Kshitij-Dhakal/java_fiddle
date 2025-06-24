package fiddle.all;

public class DoubleFiddle {

  public static final String NOT_NUMBER = "[^\\d.\\-]";

  public static void main(String[] args) {
    System.out.println(Double.parseDouble("$50.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("-$50.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("+$50.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("+$500.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("-$500.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("-$5,000.00".replaceAll(NOT_NUMBER, "")));
    System.out.println(Double.parseDouble("-$5,000,000.00".replaceAll(NOT_NUMBER, "")));

    System.out.printf("%.0f%n", 123.45);

    System.out.println(Double.compare(1, 2));
    System.out.println(Double.compare(2, 1));
    System.out.println(Double.compare(1, 1));

    System.out.printf("$%.2f%n", 10.5);
    System.out.println("$" + 10.5);
  }
}
