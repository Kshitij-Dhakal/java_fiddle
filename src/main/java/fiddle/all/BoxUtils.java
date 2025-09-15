package fiddle.all;

import com.box.sdk.*;
import java.io.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoxUtils {
  private static BoxDeveloperEditionAPIConnection api;

  @SneakyThrows
  public static void connect() {
    if (api != null) {
      log.info("Api already initialized");
      return;
    }
    var configJson =
        "{\n"
            + "  \"boxAppSettings\": {\n"
            + "    \"clientID\": \"breya6u8p04496kkvrld0vvw8iciv4xf\",\n"
            + "    \"clientSecret\": \"bz7NysE4a2ZbdZKelpGOfIxq1CBs5lnc\",\n"
            + "    \"appAuth\": {\n"
            + "      \"publicKeyID\": \"s7j5oaxl\",\n"
            + "      \"privateKey\": \"-----BEGIN ENCRYPTED PRIVATE KEY-----\\nMIIFNTBfBgkqhkiG9w0BBQ0wUjAxBgkqhkiG9w0BBQwwJAQQOROndMP/whFQIpEg\\nwmSrtwICCAAwDAYIKoZIhvcNAgkFADAdBglghkgBZQMEASoEEAUnWDmrV2dFIker\\nkRq84h4EggTQHrwZWEP/NHJ6bA7FHlIlVgs6U6A3ltyuWXyQNskJhzRJzFBl3r9D\\nYtAtMOP70iy1tkOBtZJEM/OyAPTD3/36/O+I82VEaZh5bSc6zcc17dnoP0IBcfVu\\niFhSzOMVJRmytJjnG2WrxXDvkHezcbqBIhNtC3OjtnYMxY390CabShiuGIdm6DmQ\\nIR7AredfHBWxRcroqPp5t+/FP5m2+chnjZjIaWWWk8/ylJY8pxqQvLFntozDKiqg\\nvqoSJUUNFXdS4sXlwhla53TINzoeYYNBJa/Sj3hebMPs2HKZopmUG7GMN318zvk5\\nc3CntPw4SP8sN0BETiOrWrWxxsFJLpc1uDfo/xGvHPn/32faBji5LYAUr4XlWg7c\\nBudnhaPOagI4ASCeWbnaToTO7X8BfV7dk8KP0YVB314IRO18iww7ObKxGGU6Mg4B\\n5V2a2q3nFWIhEtIeRTs7h/B3+tfANinmsL48iMH90ThkmNClEoCunYgQo09lA8Cr\\nPVh+1nqNF2xFrlpAf+xWOzHzcRX6Ed+Q44Y70eNZO+V7V3s+tvW5wr4glPvUP35b\\nKmn3X5vQd/e9crnOjHT+oL+2F5DMMDQeaCgvjjpPL2an0Ed7C0vBPc1VRLRFlnWR\\nEWrB4XvvJ7GBcBSICra/yBz5Ll0tuNgHYCRqIWP3nqILYltYl+aoZl9jJq/QDvtN\\nNnZEcR1j30m2N6yNtA5KH7xblJsfWzOGeX+r1RDc7lodjg01RU/f0+TRgzfzrfux\\n8MVRWeBOI702D5BF6e7CBzyV1XxMJ6lcFg/yMbxIcDcwTldBxSPxKHh0MlzuIhoV\\nfsilxBvXBVFG5hWEFhAymbU9aJcfhc7s96MAdcgwee2aa6F6UEE70KemzxaXgSoX\\nFdp0IX4dxvbMZMCUzDdZEuGOxQ3jElDCqLhxAFhJnxRZ9b3uKOBPd5Z4iESYRlpx\\nbF/stHy6J4ubzYOitWcq5VkYt9bJwSa4/A7vPAs3XMdOnTfJESauKZSPWXANKlzH\\nZs0pUque8AdDCyeSsAYyH4WKh+hZcWHUguO+P7JzQ+1gK8LrjI0qLhhvE8YRgpWs\\nhUMNk7XrMMhOACoNh9u2Ks6I38IJB7lDx8i1GXMOFgC6YmvgrjSZQRZfrFJ54k/X\\nx+Vh7qD3tJ81jdX8q/Co5lRGv0audV3fcEMI2WOcdFeDeLio56Hjpg60CTEcx+TS\\n76IrXQU+1zHhuIGjcjsqts/NG4RjGhntD63ppGcvLyU7fR1CET81rVVsFI68hgSK\\nZ1rzYM4bK2Sic5t6f6m59fShroZLiqMEAbmr3EfBjR5W2ibHrURbomm2WZRo/BM2\\nQ8x8K/pLoH1K31UwERbe+Ol9nXgMHILtrrrPS4MYovy5usMCR0xyL19d3AkhSjs3\\nq3SGM28PG67MbDZKOX33Anp8L96e27PN5NDKIgbtaOQb3JTUlVVC/bYSUQDp0n3k\\nZMBbEh6PKXUlUoizpFwKdtsbOSqYzvaKPiqHIL7TecBzQEfDUz3E2JVMqD5PU62E\\njFIq64IsYmBi3q2c86ZLqM/Bhq0t4H27mAUbuyMGedKmBA/bDnLWcxqFL7ELu5/z\\nQM0yuWbS21aou+6N1aUPPjdXIaBsB4f14hNimLzwp9gd+GGtYroygiA=\\n-----END ENCRYPTED PRIVATE KEY-----\\n\",\n"
            + "      \"passphrase\": \"07594e688ac6bf924165c8a14e698e03\"\n"
            + "    }\n"
            + "  },\n"
            + "  \"enterpriseID\": \"1342050034\"\n"
            + "}";
    BoxConfig boxConfig = BoxConfig.readFrom(configJson);
    IAccessTokenCache accessTokenCache = new InMemoryLRUAccessTokenCache(10);
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
    String folderId = "341201992078";
    var userId = "45288399907";
    try {
      connect();

      // 2. Get detailed folder info
      BoxFolder folder = new BoxFolder(api, folderId);
      BoxFolder.Info folderInfo =
          folder.getInfo(
              new String[] {
                "name", "permissions", "owned_by", "created_by", "item_status", "watermark_info"
              });

      log.info("Folder: {}", folderInfo.getName());
      log.info(
          "Owned by: {} ({})", folderInfo.getOwnedBy().getName(), folderInfo.getOwnedBy().getID());
      log.info(
          "Created by: {} ({})",
          folderInfo.getCreatedBy().getName(),
          folderInfo.getCreatedBy().getID());

      // 3. Check if current user is the owner
      boolean isOwner = folderInfo.getOwnedBy().getID().equals(userId);
      log.info("Is service account the owner? {}", isOwner);

      // 4. Get ALL collaborations and check status
      log.info("--- COLLABORATIONS ---");
      for (BoxCollaboration.Info collab : folder.getCollaborations()) {
        log.info("Collab ID: {}", collab.getID());
        log.info(
            "  - User: {} ({})",
            collab.getAccessibleBy().getName(),
            collab.getAccessibleBy().getID());
        log.info("  - Role: {}", collab.getRole());
        log.info("  - Status: {}", collab.getStatus());
        log.info("  - Can upload: {}", canUpload(collab.getRole()));

        // Check if this is our service account
        if (collab.getAccessibleBy().getID().equals(userId)) {
          log.info("  - ✅ THIS IS OUR SERVICE ACCOUNT");
          if (collab.getStatus() != BoxCollaboration.Status.ACCEPTED) {
            log.info("  - ❌ COLLABORATION NOT ACCEPTED: {}", collab.getStatus());
          }
        }
        log.info("  ---");
      }

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
          folderId,
          "/Users/kshitijdhakal/Desktop/app.txt",
          String.format("app_%d.txt", System.currentTimeMillis()));

    } catch (Exception e) {
      log.error("Debug error: {}", e.getMessage(), e);
    }
  }
}
