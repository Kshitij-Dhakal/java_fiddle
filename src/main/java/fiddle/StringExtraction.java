package fiddle;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;

public class StringExtraction {
  private static final String html = "";

  public static void main(String[] args) {
    var doc = Jsoup.parse(html);
    var anchorTags = doc.select("a");
    Map<String, String> result = new HashMap<>();
    for (var anchorTag : anchorTags) {
      result.put(anchorTag.text(), anchorTag.attr("href"));
    }
    System.out.println(result);
  }
}
