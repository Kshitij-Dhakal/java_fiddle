package fiddle.external.jira;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiddle.utils.AppConfig;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JiraIssueFetcher {
  private static final String JIRA_URL = AppConfig.getInstance().getProperty("jira.api.url");
  private static final String USERNAME = AppConfig.getInstance().getProperty("jira.api.username");
  private static final String API_TOKEN = AppConfig.getInstance().getProperty("jira.api.password");
  private static final String OUTPUT_FILE = "jira_issues.log";
  private static final String WORKLOG_FILE = "jira_worklogs_last_day.log";

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final HttpClient client = HttpClient.newHttpClient();

  public static void main(String[] args) throws Exception {
    List<String> keysToFind = List.of(AppConfig.getInstance().getProperty("jira.api.test.epics").split(","));
    List<String> assignees =
        List.of(AppConfig.getInstance().getProperty("jira.api.test.emails").split(","));

    Map<String, Integer> rankMap = fetchAndLogSelectedIssues(keysToFind, assignees);
    log.info("Rank map size: {}", rankMap.size());

    Map<String, List<String>> userWorklogs = fetchTasksUserWorkedOnLastDay(assignees);
    log.info("User worklog map size: {}", userWorklogs.size());
  }

  public static Map<String, Integer> fetchAndLogSelectedIssues(
      List<String> keysToFind, List<String> assignees) throws Exception {
    Set<String> remainingKeys = new HashSet<>(keysToFind);
    Map<String, Integer> keyRankMap = new LinkedHashMap<>();

    String nextPageToken = null;
    var isLast = false;
    int rankCounter = 1;

    try (var writer = new FileWriter(OUTPUT_FILE, false)) {
      var page = 1;
      while (!isLast && !remainingKeys.isEmpty()) {
        log.info("Fetching issues on page : {}", page++);
        var body = buildRequestBody(assignees, nextPageToken);

        var request =
            HttpRequest.newBuilder()
                .uri(URI.create(JIRA_URL))
                .header("Authorization", "Basic " + basicAuth(USERNAME, API_TOKEN))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        var responseBody = response.body();
        if (response.statusCode() != 200) {
          log.error("Error: {} - {}", response.statusCode(), responseBody);
          break;
        }

        var json = mapper.readTree(responseBody);
        var issues = json.get("issues");

        if (issues != null && issues.isArray()) {
          for (var issue : issues) {
            var key = issue.path("key").asText();

            var summary = issue.path("fields").path("summary").asText();
            var assignee = issue.path("fields").path("assignee").path("displayName").asText("");
            var status = issue.path("fields").path("status").path("name").asText("");

            keyRankMap.put(key, rankCounter++);
            var format = String.format("%s - %s - %s - %s%n", key, status, assignee, summary);
            writer.write(format);
            if (remainingKeys.remove(key)) {
              log.info("Found key : {}. Remaining : {}", key, remainingKeys);
            }
          }
        }

        // pagination
        isLast = json.path("isLast").asBoolean(true);
        nextPageToken = json.path("nextPageToken").asText(null);
        if (nextPageToken == null || nextPageToken.isEmpty()) {
          isLast = true;
        }
        if (isLast || remainingKeys.isEmpty()) {
          log.info("Stopping because : {} {}", isLast, remainingKeys);
          log.info("Request body : {}", body);
          log.info("Response body : {}", responseBody);
        }
      }

      log.info("Selected issues logged to " + OUTPUT_FILE);
      return keyRankMap;
    }
  }

  public static Map<String, List<String>> fetchTasksUserWorkedOnLastDay(List<String> assignees)
      throws Exception {
    Map<String, List<String>> userToKeys = new HashMap<>();

    String body = buildWorklogRequestBody(assignees);
    var request =
        HttpRequest.newBuilder()
            .uri(URI.create(JIRA_URL))
            .header("Authorization", "Basic " + basicAuth(USERNAME, API_TOKEN))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() != 200) {
      log.error("Error fetching worklogs: {} - {}", response.statusCode(), response.body());
      return userToKeys;
    }

    var json = mapper.readTree(response.body());
    JsonNode issues = json.get("issues");
    try (var writer = new FileWriter(WORKLOG_FILE, false)) {
      if (issues != null && issues.isArray()) {
        for (JsonNode issue : issues) {
          String key = issue.path("key").asText();
          JsonNode worklogs = issue.path("fields").path("worklog").path("worklogs");

          if (worklogs != null && worklogs.isArray()) {
            for (JsonNode logEntry : worklogs) {
              String author = logEntry.path("author").path("displayName").asText("");
              String created = logEntry.path("created").asText();
              String timeSpent = logEntry.path("timeSpent").asText("");

              userToKeys.computeIfAbsent(author, k -> new ArrayList<>()).add(key);
              String line = String.format("%s - %s - %s - %s%n", author, key, created, timeSpent);
              writer.write(line);
            }
          }
        }
      }
    }
    log.info("Worklog entries from last day written to {}", WORKLOG_FILE);
    return userToKeys;
  }

  @SneakyThrows
  private static String buildRequestBody(List<String> assignees, String nextPageToken) {
    Map<String, Object> body = new LinkedHashMap<>();
    if (!assignees.isEmpty()) {
      body.put(
          "jql",
          String.format(
              "project = IP2 AND status IN (\"To Do\", \"Bug\", \"In Progress\") AND assignee IN (\"%s\") AND issuetype != Epic ORDER BY Rank",
              String.join("\", \"", assignees)));
    } else {
      body.put(
          "jql",
          "project = IP2 AND status IN (\"To Do\", \"Bug\", \"In Progress\") AND issuetype != Epic ORDER BY Rank");
    }

    body.put("fields", List.of("summary", "assignee", "status"));
    body.put("maxResults", 100);

    if (nextPageToken != null && !nextPageToken.isEmpty()) {
      body.put("nextPageToken", nextPageToken);
    }
    return mapper.writeValueAsString(body);
  }

  @SneakyThrows
  private static String buildWorklogRequestBody(List<String> assignees) {
    Map<String, Object> body = new LinkedHashMap<>();
    String jql =
        String.format(
            "project = IP2 AND worklogDate >= -1d AND issuetype != Epic%s ORDER BY updated DESC",
            assignees.isEmpty()
                ? ""
                : String.format(
                    " AND worklogAuthor IN (\"%s\")", String.join("\", \"", assignees)));
    body.put("jql", jql);
    body.put("fields", List.of("summary", "worklog"));
    body.put("maxResults", 100);
    return mapper.writeValueAsString(body);
  }

  private static String basicAuth(String username, String token) {
    return Base64.getEncoder()
        .encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8));
  }
}
