package fiddle.cheat.engine;

import java.lang.reflect.Field;
import java.util.Scanner;

public class MemoryUpdater {
  public static void main(String[] args) throws Exception {
    System.out.println("Enter new money value:");
    Scanner scanner = new Scanner(System.in);
    int newMoney = scanner.nextInt();
    scanner.close();

    // Get the MemoryPrinter class
    Class<?> clazz = Class.forName("MemoryPrinter");

    // Get the static field 'money'
    Field field = clazz.getDeclaredField("money");
    field.setAccessible(true);

    // Update value
    field.setInt(null, newMoney);

    System.out.println("Money updated!");
  }
}
