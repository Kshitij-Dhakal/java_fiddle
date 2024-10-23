package fiddle;

import java.util.Deque;
import java.util.LinkedList;

public class DequeFiddle {
  public static void main(String[] args) {
    Deque<String> deque = new LinkedList<>();
    deque.add("First");
    deque.add("Second");
    deque.push("Third");
    for (String s : deque) {
      System.out.println(s);
    }
    System.out.println(deque);
    System.out.println();
    deque.push("Fourth");
    for (String s : deque) {
      System.out.println(s);
    }
    System.out.println(deque);
    System.out.println();
  }
}
