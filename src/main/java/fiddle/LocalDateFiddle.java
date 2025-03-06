package fiddle;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

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
  }
}
