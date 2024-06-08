package fiddle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.Jsoup;

public class JsoupFiddle {
  public static void main(String[] args) throws IOException {
    var html =
        Files.readString(
            Paths.get(
                "/Users/kshitijdhakal/IdeaProjects/java_fiddle/src/main/resources/static/email.html"));
    var parse = Jsoup.parse(html);
    var settings = parse.select("a:contains(Settings)");
  }
}
