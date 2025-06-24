package fiddle.all;

import java.util.List;

public class SublistFiddle {
  public static void main(String[] args) {
    List<Integer> first = List.of(1, 2, 3, 4, 5);
    List<Integer> second = List.of(1, 2, 3, 4, 5, 6, 7);
    int min = Math.min(first.size(), second.size());
    System.out.println(first.subList(0, min));
    System.out.println(second.subList(0, min));
  }
}
