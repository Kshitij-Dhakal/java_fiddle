package fiddle;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;

public class StringsFiddle {
  public static void main(String[] args) {
        String intro = "Hello world!";
        System.out.println(intro.substring(0, intro.length() - 1));

        var str1 = new String("?".getBytes(), StandardCharsets.UTF_8);
        var str2 = new String("?".getBytes(), StandardCharsets.US_ASCII);
        System.out.println(str1.equals(str2));
        System.out.println("?".equals("?"));

        System.out.println(
            getAlphaNumericCharactersOnly(
                "DEV post ? States Where Unemployment Claims Are Decreasing the Most_1"));

        System.out.println(
            "Save $431* with the Capital One SavorOne Cash Rewards Credit Card"
                .matches("^Save \\$\\d+\\* with the Capital One SavorOne Cash Rewards Credit Card$"));

    System.out.println(charToStringWithEncoding(8211, StandardCharsets.UTF_8));
    String utf8Qm = charToStringWithEncoding(63, StandardCharsets.UTF_8);
    System.out.println(utf8Qm);
    String isoQmAlt = charToStringWithEncoding(8211, StandardCharsets.ISO_8859_1);
    System.out.println(isoQmAlt);
    String isoQm = charToStringWithEncoding(63, StandardCharsets.ISO_8859_1);
    System.out.println(isoQm);

    System.out.println(StringUtils.equals(utf8Qm, isoQmAlt));
    System.out.println(StringUtils.equals(utf8Qm, isoQm));
    System.out.println(StringUtils.equals(isoQmAlt, isoQm));

    System.out.println(utf8Qm.equals(isoQmAlt));
    System.out.println(utf8Qm.equals(isoQm));
    System.out.println(isoQmAlt.equals(isoQm));
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
