package fiddle.all;

import java.text.DecimalFormat;
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
    DecimalFormat df = new DecimalFormat("#0.##");

    System.out.println(df.format(5));
    System.out.println(df.format(55));
    System.out.println(df.format(555));
    System.out.println(df.format(5.0));
    System.out.println(df.format(55.0));
    System.out.println(df.format(555.0));
    System.out.println(df.format(5.05));
    System.out.println(df.format(55.05));
    System.out.println(df.format(555.05));
    System.out.println(df.format(5.9));
    System.out.println(df.format(55.9));
    System.out.println(df.format(555.9));
    System.out.println(df.format(5.95));
    System.out.println(df.format(55.95));
    System.out.println(df.format(555.95));

    df = new DecimalFormat("#0");

    System.out.println(df.format(5));
    System.out.println(df.format(55));
    System.out.println(df.format(555));
    System.out.println(df.format(5.0));
    System.out.println(df.format(55.0));
    System.out.println(df.format(555.0));
    System.out.println(df.format(5.05));
    System.out.println(df.format(55.05));
    System.out.println(df.format(555.05));
    System.out.println(df.format(5.9));
    System.out.println(df.format(55.9));
    System.out.println(df.format(555.9));
    System.out.println(df.format(5.95));
    System.out.println(df.format(55.95));
    System.out.println(df.format(555.95));

    System.out.println(Math.round(5.0));
    System.out.println(Math.round(5.1));
    System.out.println(Math.round(5.2));
    System.out.println(Math.round(5.3));
    System.out.println(Math.round(5.4));
    System.out.println(Math.round(5.5));
    System.out.println(Math.round(5.6));
    System.out.println(Math.round(5.7));
    System.out.println(Math.round(5.8));
    System.out.println(Math.round(5.9));

    System.out.println("Estimated: $44.00".replaceAll("[^\\d$.,-]", ""));
    System.out.println("Estimated: $44.44".replaceAll("[^\\d$.,-]", ""));
    System.out.println("Estimated: -$44.00".replaceAll("[^\\d$.,-]", ""));
    System.out.println("Estimated: $-44.00".replaceAll("[^\\d$.,-]", ""));
    System.out.println("Estimated: $-4,4.00".replaceAll("[^\\d$.,-]", ""));
  }
}
