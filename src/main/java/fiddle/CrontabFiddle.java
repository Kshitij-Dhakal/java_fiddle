package fiddle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrontabFiddle {
  public static void main(String[] args) {
    String filePath =
        "/Users/kshitijdhakal/IdeaProjects/java_fiddle/src/main/resources/static/crontab";
    Map<String, List<String>> timeToTestsMap = new HashMap<>();
    Set<String> allTestIds = new HashSet<>();
    Set<String> duplicateTestIds = new HashSet<>();

    Pattern pattern =
        Pattern.compile(
            "(\\d+) (\\d+) \\* \\* \\* .*?\\\\\"TEST_CASE_VALUES\\\\\":\\\\\"(.*?)\\\\\".*?");

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
          String minute = matcher.group(1);
          String hour = matcher.group(2);
          String testCases = matcher.group(3);
          String timeKey = hour + ":" + minute;

          List<String> testIds = Arrays.asList(testCases.split(","));

          timeToTestsMap.putIfAbsent(timeKey, new ArrayList<>());
          timeToTestsMap.get(timeKey).addAll(testIds);

          for (String testId : testIds) {
            if (!allTestIds.add(testId)) {
              duplicateTestIds.add(testId);
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Print the time-to-tests map
    log.info("<<<Time -> Test IDs Map:>>>");
    for (Map.Entry<String, List<String>> entry : timeToTestsMap.entrySet()) {
      log.info(entry.getKey() + " -> " + entry.getValue());
    }

    // Print duplicate test IDs
    log.info("<<<Duplicate Test IDs>>>");
    log.info("{}", duplicateTestIds);

    log.info("<<<Sorted>>>");
    StringBuilder bldr = new StringBuilder("\n");
    timeToTestsMap.entrySet().stream()
        .sorted((o1, o2) -> Integer.compare(o1.getValue().size(), o2.getValue().size()))
        .forEach(
            e -> bldr.append(e.getKey()).append(" -> ").append(e.getValue().size()).append("\n"));
    log.info(bldr.toString());
  }
}
