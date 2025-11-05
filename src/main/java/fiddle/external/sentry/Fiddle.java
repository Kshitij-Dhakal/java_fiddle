package fiddle.external.sentry;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Fiddle {
  @SneakyThrows
  public static void main(String[] args) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime end = now.withMinute(0).withSecond(0).withNano(0);
    LocalDateTime start = end.minusHours(24);

    System.out.println(start.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println(end.format(DateTimeFormatter.ISO_DATE_TIME));
    var sentryHelper = new SentryHelper();
    var finicityIssuesFromSentry = sentryHelper.getLastDayIssues();
    log.info("{}", JsonUtil.toJson(finicityIssuesFromSentry));

    Instant instant = Instant.parse("2025-10-29T15:35:58.644999Z");
    ZonedDateTime nyTime = instant.atZone(ZoneId.of("America/New_York"));
    System.out.println(nyTime);
  }
}
