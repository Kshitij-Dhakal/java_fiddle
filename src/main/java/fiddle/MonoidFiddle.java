package fiddle;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

public class MonoidFiddle {
  public static void main(String[] args) throws JSONException {
    JSONObject jsonObject =
        new JSONObject(
            "{\n"
                + "  \"privateId\": \"Test 123\",\n"
                + "  \"email\": \"kshitij@gmail.com\"\n"
                + "}");
    System.out.println(jsonObject.get("privateId"));
  }
}
