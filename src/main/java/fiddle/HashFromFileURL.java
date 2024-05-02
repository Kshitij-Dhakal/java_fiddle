package fiddle;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;

public class HashFromFileURL {
  public static void main(String[] args) throws Exception {
    System.out.println(
        "SHA-256 hash: "
            + getHash(
                "https://cdn.efdevhub.info/images/ng2-images/transactions/category-icon-sprite_Vb3175d3_.svg.gz",
                "SHA-256"));
    System.out.println(
        "SHA-256 hash: "
            + getHash(
                "https://wallethu.testrail.net/index.php?/attachments/get/ee6f6ab4-9522-48f7-a044-223ea2fedc23",
                "SHA-256"));
  }

  public static String getHash(String url, String algorithm) throws Exception {
    var fileUrl = new URL(url);
    try (var is = fileUrl.openStream()) {
      var digest = MessageDigest.getInstance(algorithm);
      var buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = is.read(buffer)) != -1) {
        digest.update(buffer, 0, bytesRead);
      }
      var hash = digest.digest();
      return new BigInteger(1, hash).toString(16);
    }
  }
}
