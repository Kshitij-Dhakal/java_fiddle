package fiddle;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DateTimeFiddle {
  public static void main(String[] args) {
    System.out.println(Calendar.getInstance().get(Calendar.YEAR));
    var all = new String[] {};

    var spreadSheet = new String[] {};

    var x1 = Lists.newArrayList(all);
    var x2 = Lists.newArrayList(spreadSheet);
    x1.removeAll(x2);
    System.out.println(x1);

    LocalDate localDate = LocalDate.of(2025, 5, 1);
    System.out.println(localDate);
    localDate = localDate.minusDays(31);
    System.out.println(localDate);
    System.out.println(localDate.minusMonths(1));

    System.out.println(LocalDateTime.now().minusDays(35));
  }
}
