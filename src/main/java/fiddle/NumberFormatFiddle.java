package fiddle;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatFiddle {
  public static void main(String[] args) {
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(1));
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(10));
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(100));
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(1000));
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(10000));
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(100000));
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(1000000));
  }
}
