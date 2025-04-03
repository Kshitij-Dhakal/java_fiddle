package fiddle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class DateParser {
  public static void main(String[] args) {
    System.out.println(new DateParser().getDateOnBalanceSection());
    System.out.println("-3.5 test".replaceAll("[^\\d-.]", ""));

    System.out.println(
        LocalDate.parse("Oct 25, 2024", DateTimeFormatter.ofPattern("MMM dd, yyyy")));
  }

  public LocalDate getDateOnBalanceSection() {
    String balanceSectionTextTrimmed = "$3,110 as of Apr 27, 2015 • update";
    var pattern = Pattern.compile("\\$[\\d,]+ as of ([A-Za-z]{3} \\d{1,2}(, \\d{4})?) • update");
    var matcher = pattern.matcher(balanceSectionTextTrimmed);
    if (!matcher.find()) {
      log.info("Failed to match pattern. Returning null as date.");
      return null;
    }
    String group = matcher.group(1);
    log.info("Group : {}", group);
    DateTimeFormatter formatter;
    if (StringUtils.contains(group, ",")) {
      formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
    } else {
      formatter = DateTimeFormatter.ofPattern("MMM d");
    }
    return LocalDate.parse(group, formatter);
  }
}
