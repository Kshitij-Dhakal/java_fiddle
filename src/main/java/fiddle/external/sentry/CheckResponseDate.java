package fiddle.external.sentry;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CheckResponseDate {
  public static void main(String[] args) {
    String isoUtc = "2025-10-29T16:16:33.999884Z";
    Instant instant = Instant.parse(isoUtc);
    ZoneId localZone = ZoneId.systemDefault();
    ZonedDateTime zonedLocal = instant.atZone(localZone);
    LocalDateTime localDateTime = zonedLocal.toLocalDateTime();
    System.out.println("System zone: " + localZone);
    System.out.println("LocalDateTime: " + localDateTime);
  }
}
