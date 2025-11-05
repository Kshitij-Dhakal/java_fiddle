package fiddle.utils;

import java.io.IOException;
import java.io.InputStream;

public class AppConfig {
  private static final java.util.Properties properties = new java.util.Properties();
  private static AppConfig instance = null;

  private AppConfig() {
    try (InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream("app.properties")) {
      if (inputStream == null) {
        throw new RuntimeException("app.properties not found in resources");
      }
      properties.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load app.properties", e);
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
    String value = properties.getProperty(key);
    if (value != null) {
      try {
        return Integer.parseInt(value.trim());
      } catch (NumberFormatException ignored) {
      }
    }
    return defaultValue;
  }

  public boolean getBooleanProperty(String key, boolean defaultValue) {
    String value = properties.getProperty(key);
    if (value != null) {
      return Boolean.parseBoolean(value.trim());
    }
    return defaultValue;
  }
}
