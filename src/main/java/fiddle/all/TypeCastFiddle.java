package fiddle.all;

public class TypeCastFiddle {
  public static void main(String[] args) {
    System.out.println(Integer.MAX_VALUE);
    System.out.println((int) System.currentTimeMillis());
    System.out.println(System.currentTimeMillis());

    for (int i = 0; i < 1000; i++) {
      int x = (int) (System.currentTimeMillis() % 3);
      if (x < 0) {
        System.out.println(x);
      }
    }
  }
}
