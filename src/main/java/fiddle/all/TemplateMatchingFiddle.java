package fiddle.all;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateMatchingFiddle {

  public static boolean matchesTemplate(String template, String generatedText) {
    template = template.replace("?", "\\?");
    template = template.replace("$", "\\$");
    template = template.replace("(", "\\(");
    template = template.replace(")", "\\)");
    template = template.replace("/", "\\/");
    String regex = template.replaceAll("\\{\\{\\s*[^}]+\\s*\\}\\}", "(.+?)");
    regex = "^" + regex + "$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(generatedText);
    return matcher.find();
  }

  // Demo
  public static void main(String[] args) {
    String template = "Hello {{name}}, your order {{order_id}} is ready.";
    String generated1 = "Hello Alice, your order 12345 is ready.";
    String generated2 = "Hi Alice, your order 12345 is ready.";

    System.out.println(matchesTemplate(template, generated1)); // true
    System.out.println(matchesTemplate(template, generated2)); // false
    System.out.println(
        matchesTemplate("Sign up for ${{amt}}/mo", "Sign up for $3.99/mo (50% off)")); // false
  }
}
