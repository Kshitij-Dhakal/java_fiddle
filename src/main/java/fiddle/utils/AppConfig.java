package fiddle.utils;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfig {
  private static final java.util.Properties properties = new java.util.Properties();
  private static AppConfig instance = null;

  private AppConfig() {
    loadProperties("app.properties", true);
    loadProperties("app-local.properties", false);
    loadProperties("app-secret.properties", false);
  }

  private void loadProperties(String filename, boolean required) {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
      if (inputStream == null) {
        String message = filename + " not found in resources";
        if (required) {
          throw new RuntimeException(message);
        } else {
          log.warn(message);
          return;
        }
      }
      properties.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load " + filename, e);
    }
  }

  public static AppConfig getInstance() {
    if (instance == null) {
      synchronized (AppConfig.class) {
        if (instance == null) {
          instance = new AppConfig();
        }
      }
    }
    return instance;
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  public int getIntProperty(String key, int defaultValue) {
    var value = properties.getProperty(key);
    if (value != null) {
      try {
        return Integer.parseInt(value.trim());
      } catch (NumberFormatException ignored) {
      }
    }
    return defaultValue;
  }

  public boolean getBooleanProperty(String key, boolean defaultValue) {
    var value = properties.getProperty(key);
    if (value != null) {
      return Boolean.parseBoolean(value.trim());
    }
    return defaultValue;
  }
}
