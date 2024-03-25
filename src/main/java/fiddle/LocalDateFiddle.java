package fiddle;

import java.time.LocalDate;
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
  }
}
