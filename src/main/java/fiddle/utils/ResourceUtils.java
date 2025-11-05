package fiddle.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceUtils {

  private ResourceUtils() {}

  /**
   * Reads a file from the resources folder and returns its content as a string.
   *
   * @param resourcePath the path to the resource (e.g., "data/sample.json")
   * @return content of the file as String
   * @throws RuntimeException if resource not found or I/O error occurs
   */
  public static String readResourceAsString(String resourcePath) {
    log.info("Reading resource : {}", resourcePath);
    try (var inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
      if (inputStream == null) {
        throw new RuntimeException("Resource not found: " + resourcePath);
      }

      try (var reader =
          new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(Collectors.joining("\n"));
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to read resource: " + resourcePath, e);
    }
  }

  /**
   * Writes a string to a file in the file system (not the resources folder).
   *
   * @param relativePath path relative to the working directory (e.g., "output/data.json")
   * @param content the content to write
   */
  @SneakyThrows
  public static void writeStringToFile(String relativePath, String content) {
    log.info("Writing to resource : {}", relativePath);
    try {
      Path path = Paths.get(relativePath);
      Files.createDirectories(path.getParent()); // Create directories if needed
      Files.writeString(path, content);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write to file: " + relativePath, e);
    }
  }
}
