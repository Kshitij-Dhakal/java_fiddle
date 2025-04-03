package fiddle;

import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import lombok.SneakyThrows;

public class FilesFiddle {

  @SneakyThrows
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      usingFiles("Test");
    }
  }

  private static void usingFiles(String msg) throws IOException {
    Files.writeString(
        Path.of("/Users/kshitijdhakal/IdeaProjects/java_fiddle/out/user.csv"),
        msg,
        StandardOpenOption.APPEND);
  }

  private static void usingCharSink(String msg) throws IOException {
    CharSink sink =
        com.google.common.io.Files.asCharSink(
            new File("/Users/kshitijdhakal/IdeaProjects/java_fiddle/out/user.csv"),
            StandardCharsets.UTF_8,
            FileWriteMode.APPEND);
    sink.write(msg);
  }
}
