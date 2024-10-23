package fiddle;

import java.util.LinkedList;

public class ListFiddle {
  public static void main(String[] args) {
    var list = new LinkedList<String>();
    list.add("A");
    list.add("B");
    list.add(null);
    list.add("C");
    list.add("D");
    System.out.println(list);
  }
}
