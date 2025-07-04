package fiddle.all;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalDateFiddle {
  public static void main(String[] args) {
    System.out.println(DateTimeFormatter.ofPattern("MMM").format(LocalDate.now()));
    var df =
        new DateTimeFormatterBuilder()
            .appendPattern("MMM dd")
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter();
    System.out.println(LocalDate.parse("Jan 23", df));

    System.out.println(LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC));
    ZoneId zoneId = ZoneId.of("America/New_York");
    System.out.println(LocalDate.now().atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond());

    LocalDate mar31 = LocalDate.of(2024, 3, 31);
    System.out.println(mar31.minusMonths(1));
    System.out.println(mar31.minusDays(1).minusMonths(1));

    System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    System.out.println("<<< Date reported >>>");
    var dateReported = LocalDateTime.now().plusDays(3).minusMonths(1);
    System.out.println(dateReported);
    System.out.println(calculateDueDateBasedOnDateReported(dateReported, true));
    System.out.println(calculateDueDateBasedOnDateReported(dateReported.minusDays(1), true));
    System.out.println(calculateDueDateBasedOnDateReported(dateReported.minusDays(2), true));
    System.out.println(calculateDueDateBasedOnDateReported(dateReported.minusDays(3), true));
    System.out.println(calculateDueDateBasedOnDateReported(dateReported.minusDays(4), true));

    System.out.println("<<< Push Synced >>>");
    for (int i = 0; i < 30; i++) {
      System.out.printf("%d => %s%n", i + 1, nextXthDayOfTheMonth(i + 1, true));
    }
    System.out.println("<<< Push Not Synced >>>");
    for (int i = 0; i < 30; i++) {
      System.out.printf("%d => %s%n", i + 1, nextXthDayOfTheMonth(i + 1, false));
    }
    System.out.println("<<< Date reported Synced >>>");
    for (int i = 0; i < 30; i++) {
      System.out.printf("%d => %s%n", i + 1, calculateDueDateBasedOnDateReported(i + 1, true));
    }
    System.out.println("<<< Date reported Not Synced >>>");
    for (int i = 0; i < 30; i++) {
      System.out.printf("%d => %s%n", i + 1, calculateDueDateBasedOnDateReported(i + 1, true));
    }

    System.out.println("<<< Days after >>>");
    for (int i = 0; i < 20; i++) {
      int dayOfMonth = i + 1;
      var ld = LocalDate.now().withDayOfMonth(dayOfMonth);
      var diff = ld.getDayOfMonth() - 4;
      System.out.printf("%d => %s %b%n", dayOfMonth, diff, diff > 9);
    }



  }

  public static LocalDate calculateDueDateBasedOnDateReported(int date, boolean synced) {
    LocalDateTime localDateTime = LocalDateTime.now().withDayOfMonth(date);
    return nextXthDayOfTheMonth(localDateTime.minusDays(6).getDayOfMonth(), synced);
  }

  public static LocalDate calculateDueDateBasedOnDateReported(LocalDateTime date, boolean synced) {
    var dayOfMonth = date.minusDays(6).getDayOfMonth();
    return nextXthDayOfTheMonth(dayOfMonth, synced);
  }

  public static LocalDate nextXthDayOfTheMonth(int dayOfMonth, boolean synced) {
    LocalDate dueDate;
    if (dayOfMonth > LocalDate.now().lengthOfMonth()) {
      dueDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    } else {
      dueDate = LocalDate.now().withDayOfMonth(dayOfMonth);
    }
    if (dueDate.getDayOfMonth() > 28) {
      dueDate = dueDate.withDayOfMonth(dueDate.lengthOfMonth());
    }
    if (synced) {
      if (dueDate.plusDays(9).isBefore(LocalDate.now())) {
        dueDate = dueDate.plusMonths(1);
      }
    } else {
      if (dueDate.isBefore(LocalDate.now())) {
        dueDate = dueDate.plusMonths(1);
      }
    }
    return dueDate;
  }
}
