package fiddle;

public class OrderOfOperationFiddle {
  public static String m1() {
    System.out.println("Method 1");
    return "";
  }

  public static void m2(String str) {
    System.out.println("Method 2");
  }

  public static void main(String[] args) {
    m2(m1());
  }
}
