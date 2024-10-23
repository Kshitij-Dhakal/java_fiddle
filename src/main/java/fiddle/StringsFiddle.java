package fiddle;

import java.nio.charset.Charset;

public class StringsFiddle {
  public static void main(String[] args) {
    String str =
        "This rule will affect 12 transactions.\n" + "\n" + "Would you like to apply the rule?";
    System.out.println(
        str.matches("This rule will affect \\d+ transactions\\.\\n+Would you like to apply the rule\\?"));

    System.out.println("Always ignore transactions from Credit Card (**1356)".matches("Always ignore transactions from .*"));
    System.out.println("Always mark Credit Card (**1356) transactions as recurring".matches("Always mark .* transactions as recurring"));
    System.out.println("Always modify 'Credit Card (**1356)' transactions as follows: a) set to 'ignore' and b) mark as recurring".matches("Always modify '.*' transactions as follows: a\\) set to 'ignore' and b\\) mark as recurring"));
    System.out.println(("Always modify 'Credit Card (**1356)' transactions as follows: a) categorize " +
            "as 'Auto & Transport', b) prioritize as 'must-have', c) set to 'ignore', d) mark as " +
            "recurring, and e) label as 'Birthday'")
            .matches("Always modify '.*' transactions as follows: a\\) categorize " +
            "as '.*', b\\) prioritize as 'must-have', c\\) set to 'ignore', d\\) mark as " +
            "recurring, and e\\) label as '.*'"));
  }

  public static String removePostfix(String original, String postfix) {
    if (original.endsWith(postfix)) {
      return original.substring(0, original.length() - postfix.length());
    }
    return original;
  }

  private static String getAlphaNumericCharactersOnly(String pageTitle) {
    return pageTitle.replaceAll("[^a-zA-Z0-9 ]", "");
  }

  public static String charToStringWithEncoding(int codePoint, Charset charset) {
    if (!Character.isValidCodePoint(codePoint)) {
      throw new IllegalArgumentException("Invalid Unicode code point: " + codePoint);
    }
    char[] chars;
    if (Character.isSupplementaryCodePoint(codePoint)) {
      chars = Character.toChars(codePoint);
    } else {
      chars = new char[] {(char) codePoint};
    }
    byte[] bytes = new String(chars).getBytes(charset);
    return new String(bytes, charset);
  }
}
