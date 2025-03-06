package fiddle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFiddle {
  public static void main(String[] args) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Book book = new Book();
    book.setTitle("Test 123");
    System.out.println(gson.toJson(book));
  }
}
