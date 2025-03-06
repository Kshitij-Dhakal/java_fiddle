package fiddle;

import com.google.gson.GsonBuilder;

public class JsonFiddle {
  public static void main(String[] args) {
    System.out.println(stringify("Requires good/excellent credit score"));
    Book book = new Book();
    book.setTitle("Requires good/excellent credit score");
    System.out.println(stringify(book));
  }

  public static String stringify(Object obj) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
  }
}
