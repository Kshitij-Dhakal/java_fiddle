package fiddle.all.box;

import com.box.sdk.*;
import java.io.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoxUtils {
  private static BoxDeveloperEditionAPIConnection api;
  private static final BoxDetails boxDetails = new MyBoxDetails();

  @SneakyThrows
  public static void connect() {
    if (api != null) {
      log.info("Api already initialized");
      return;
    }
    var configJson = boxDetails.json();
    var boxConfig = BoxConfig.readFrom(configJson);
    var accessTokenCache = new InMemoryLRUAccessTokenCache(10);
    api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(boxConfig, accessTokenCache);
  }

  @SneakyThrows
  public static void uploadFile(String folderId, String localPath, String uploadName) {
    var folder = new BoxFolder(api, folderId);
    try (var stream = new FileInputStream(localPath)) {
      folder.uploadFile(stream, uploadName);
    }
  }

  @SneakyThrows
  public static void downloadFile(String fileId, String outputPath) {
    var file = new BoxFile(api, fileId);
    try (var stream = new FileOutputStream(outputPath)) {
      file.download(stream);
    }
  }

  @SneakyThrows
  public static void listFolderContents(String folderId) {
    var folder = new BoxFolder(api, folderId);
    String[] fields = {"name", "permissions", "id", "created_at"};
    var info = folder.getInfo(fields);
    log.info("folder : {} - {} - {}", info.getName(), info.getID(), info.getPermissions());
    for (var itemInfo : folder) {
      log.info("[{}] {} (ID: {})", itemInfo.getType(), itemInfo.getName(), itemInfo.getID());
    }
  }

  @SneakyThrows
  public static void createFolder(String parentFolderId, String folderName) {
    var parent = new BoxFolder(api, parentFolderId);
    parent.createFolder(folderName);
  }

  @SneakyThrows
  public static void deleteFile(String fileId) {
    var file = new BoxFile(api, fileId);
    file.delete();
  }

  @SneakyThrows
  public static void deleteFolder(String folderId, boolean recursive) {
    var folder = new BoxFolder(api, folderId);
    folder.delete(recursive);
  }

  private static boolean canUpload(BoxCollaboration.Role role) {
    return role == BoxCollaboration.Role.EDITOR
        || role == BoxCollaboration.Role.CO_OWNER
        || role == BoxCollaboration.Role.UPLOADER;
  }

  public static void main(String[] args) throws Exception {
    String folderId = boxDetails.folderId();
    try {
      connect();
      // 2. Get detailed folder info
      BoxFolder folder = new BoxFolder(api, folderId);
      BoxFolder.Info folderInfo =
          folder.getInfo(
              "name", "permissions", "owned_by", "created_by", "item_status", "watermark_info");

      log.info("Folder: {}", folderInfo.getName());
      log.info(
          "Owned by: {} ({})", folderInfo.getOwnedBy().getName(), folderInfo.getOwnedBy().getID());
      log.info(
          "Created by: {} ({})",
          folderInfo.getCreatedBy().getName(),
          folderInfo.getCreatedBy().getID());
      log.info("{}", folderInfo.getOwnedBy());

      // 4. Get ALL collaborations and check status
      log.info("--- COLLABORATIONS ---");
      //      for (BoxCollaboration.Info collab : folder.getCollaborations()) {
      //        log.info("Collab ID: {}", collab.getID());
      //        log.info(
      //            "  - User: {} ({})",
      //            collab.getAccessibleBy().getName(),
      //            collab.getAccessibleBy().getID());
      //        log.info("  - Role: {}", collab.getRole());
      //        log.info("  - Status: {}", collab.getStatus());
      //        log.info("  - Can upload: {}", canUpload(collab.getRole()));
      //        log.info("  ---");
      //      }

      // 5. Try a simple upload with detailed error handling
      log.info("--- UPLOAD TEST ---");
      try {
        String testContent = "test content " + System.currentTimeMillis();
        BoxFile.Info testFile =
            folder.uploadFile(new ByteArrayInputStream(testContent.getBytes()), "test_upload.txt");
        log.info("✅ UPLOAD SUCCESS! File ID: {}", testFile.getID());

        // Clean up
        testFile.getResource().delete();

      } catch (BoxAPIException e) {
        log.error("❌ UPLOAD FAILED: {}", e.getMessage());
        log.error("Response Code: {}", e.getResponseCode());
        if (e.getResponse() != null) {
          log.error("Response Body: {}", e.getResponse());
        }
        log.error("Headers: {}", e.getHeaders());
      }

      uploadFile(
          boxDetails.folderId(),
          "/Users/kshitijdhakal/Desktop/app.txt",
          "test_upload_from_automation.txt");
    } catch (Exception e) {
      log.error("Debug error: {}", e.getMessage(), e);
    }
  }
}
