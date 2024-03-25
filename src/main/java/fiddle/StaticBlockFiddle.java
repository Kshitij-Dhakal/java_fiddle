package fiddle;

public class StaticBlockFiddle {
  public static void main(String[] args) {
    System.out.println("Running!");
    {
      System.out.println("Running inside static block");
    }
  }
}
