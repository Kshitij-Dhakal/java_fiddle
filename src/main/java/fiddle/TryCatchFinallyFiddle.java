package fiddle;

public class TryCatchFinallyFiddle {
  public static void main(String[] args) {
    throwsError();
  }

  public static void throwsError() {
    try {
      System.out.println("Try");
      throw new RuntimeException();
    } catch (Exception e) {
      System.out.println("Catch");
    } finally {
      System.out.println("Finally");
    }
  }
}
