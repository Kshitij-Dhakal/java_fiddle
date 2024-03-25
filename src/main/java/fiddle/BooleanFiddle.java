package fiddle;

public class BooleanFiddle {
  public static void main(String[] args) {
    // xor test
    System.out.println(false);
    System.out.println(true);
    System.out.println(true);
    System.out.println(false);
    testVarArg();
  }

  public static void testVarArg(Object... args) {
    System.out.println(args.length);
  }
}
