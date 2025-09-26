package fiddle.wh;

import com.opencsv.CSVReaderHeaderAware;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CsvFailureReport {
  public static final String RESET = "\u001B[0m";
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String PRINT_FORMAT = "%-35s %10s\n";

  public static void main(String[] args) {
    Map<String, Long> failureCountByCategory = new TreeMap<>();

    try (var inputStream =
        CsvFailureReport.class
            .getClassLoader()
            .getResourceAsStream("company/automated-test-results.csv")) {
      assert inputStream != null;
      try (var reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
          var csvReader = new CSVReaderHeaderAware(reader)) {
        Map<String, String> row;
        while ((row = csvReader.readMap()) != null) {
          var category = row.get("Category");
          var failuresStr = row.get("Number of Failures");

          if (failuresStr != null && !failuresStr.isBlank()) {
            processNumberOfFailures(failuresStr, failureCountByCategory, category);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.printf(PRINT_FORMAT, "Category", "Failures");
    // Sort and print the categories by number of failed tests in descending order
    failureCountByCategory.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .forEach(entry -> printRow(entry.getKey(), entry.getValue()));
    long total = 0;
    for (long value : failureCountByCategory.values()) {
      total = total + value;
    }
    System.out.println(total);

    System.out.println("<<< Fixed Categories >>>");
    var fixedCategories =
        List.of(
            "Budgeting",
            "Transactions",
            "Payments",
            "Plan Change",
            "WalletHub Premium",
            "Account Sharing",
            "Recurring",
            "Ithaca Rapid",
            "Credit Cards",
            "WalletScore",
            "Timeline",
            "Disclosure",
            "Misc Pages");
    System.out.printf(PRINT_FORMAT, "Category", "Failures");
    for (var fixedCategory : fixedCategories) {
      var category = fixedCategory;
      var count = failureCountByCategory.get(fixedCategory);
      printRow(fixedCategory, count);
    }
  }

  private static void printRow(String fixedCategory, Long count) {
    String formattedCount = String.format("%10d", count); // pad the number first
    String coloredCount = (count == 0 ? GREEN : RED) + formattedCount + RESET;
    System.out.printf(PRINT_FORMAT, fixedCategory, coloredCount);
  }

  private static void processNumberOfFailures(
      String failuresStr, Map<String, Long> failureCountByCategory, String category) {
    try {
      var failures = Integer.parseInt(failuresStr.trim());
      if (failures > 1) {
        failureCountByCategory.put(category, failureCountByCategory.getOrDefault(category, 0L) + 1);
      } else {
        failureCountByCategory.put(category, failureCountByCategory.getOrDefault(category, 0L));
      }
    } catch (NumberFormatException e) {
      System.err.println("Skipping malformed failure count: " + failuresStr);
    }
  }
}
