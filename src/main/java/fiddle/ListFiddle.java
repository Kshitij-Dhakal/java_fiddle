package fiddle;

import java.util.LinkedList;
import java.util.List;

public class ListFiddle {
  public static void main(String[] args) {
    var list = new LinkedList<String>();
    list.add("A");
    list.add("B");
    list.add(null);
    list.add("C");
    list.add("D");
    System.out.println(list);

    List<String> todo = List.of("TODO", "Test on dev", "In progress", "Not needed", "Closed");
    System.out.println(todo.subList(todo.size() - 2, todo.size()));

    for (int i = 0; i < 11; i++) {
      System.out.printf("selenium_user%03d@evolutionfinance.com%n", i);
    }

    for (int i = 0; i < 20; i++) {
      System.out.println(list.get(i % list.size()));
    }
  }
}
