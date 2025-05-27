package fiddle;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.WordUtils;

public class StringsFiddle {
  public static void main(String[] args) {
    var str = "202, 201, 203, 204";
    List<String> list =
        Arrays.stream(str.split(",")).map(StringUtils::trim).map(e -> "$" + e + ".00").toList();
    System.out.printf("\"%s\"%n", String.join("\", \"", list));

    String x = "test?123";
    System.out.println(x.substring(0, x.indexOf("?")));

    System.out.println("Inalotom1".replaceAll("\\D", ""));

    String s = "Uber\nAlways modify as";
    System.out.println(s.substring(s.indexOf("Always")));

    System.out.println(
        StringSubstitutor.replace(
            "Test ${firstname} ${surname} ${firstname}",
            Map.of("firstname", "Kshitij", "surname", "Dhakal")));

    System.out.println(WordUtils.capitalize("kshitij dhakal 123 check"));

    System.out.println("Hello Ryan".substring("Hello ".length()));

    String ssn = "666416237";
    System.out.println(ssn.substring(ssn.length() - 4));
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
