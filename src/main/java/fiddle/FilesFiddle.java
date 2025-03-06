package fiddle;

import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import java.io.File;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;

public class FilesFiddle {
  @SneakyThrows
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      CharSink sink =
          Files.asCharSink(
              new File(
                  "/Users/kshitijdhakal/IdeaProjects/java_fiddle/src/main/resources/static",
                  "test"),
              StandardCharsets.UTF_8,
              FileWriteMode.APPEND);
      sink.write("Test");
    }
  }
}
