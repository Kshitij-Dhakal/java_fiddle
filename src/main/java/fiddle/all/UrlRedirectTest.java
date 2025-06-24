package fiddle.all;

import java.net.HttpURLConnection;
import java.net.URL;

public class UrlRedirectTest {
  public static void main(String[] args) {
    String url1 = "";
    String url2 = "";

    if (areURLsEquivalent(url1, url2)) {
      System.out.println("The URLs reach/redirect to the same page.");
    } else {
      System.out.println("The URLs do not reach/redirect to the same page.");
    }
  }

  public static boolean areURLsEquivalent(String url1, String url2) {
    try {
      HttpURLConnection connection1 = (HttpURLConnection) new URL(url1).openConnection();
      connection1.setInstanceFollowRedirects(false);
      connection1.connect();

      HttpURLConnection connection2 = (HttpURLConnection) new URL(url2).openConnection();
      connection2.setInstanceFollowRedirects(false);
      connection2.connect();

      int responseCode1 = connection1.getResponseCode();
      int responseCode2 = connection2.getResponseCode();

      if (responseCode1 == HttpURLConnection.HTTP_OK
          && responseCode2 == HttpURLConnection.HTTP_OK) {
        return url1.equals(url2);
      } else {
        String location1 = connection1.getHeaderField("Location");
        String location2 = connection2.getHeaderField("Location");

        System.out.println(location1);
        System.out.println(location2);
        if (location1 != null && location2 != null) {
          return location1.equals(location2);
        }
      }

      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
