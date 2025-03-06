package fiddle;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FoldersFiddle {
  public static void main(String[] args) {
    deleteFolderRecursively(new File("/Users/kshitijdhakal/Desktop/pagecache/join"));
    System.out.println(Files.exists(Paths.get("/Users/kshitijdhakal/Desktop", "fileName")));
  }

  public static void deleteFolderRecursively(File folder) {
    if (folder.exists()) {
      File[] files = folder.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isDirectory()) {
            deleteFolderRecursively(file);
          } else {
            file.delete();
          }
        }
      }
      folder.delete();
    }
  }
}
