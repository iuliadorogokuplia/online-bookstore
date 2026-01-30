package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPERTIES = new Properties();
    private static final String CONFIG_PATH = "config.properties";

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
            if (is == null) {
                throw new RuntimeException("Configuration file not found in classpath: " + CONFIG_PATH);
            }
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    private static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isEmpty()) {
            value = System.getenv(key);
        }
        if (value == null || value.isEmpty()) {
            value = PROPERTIES.getProperty(key);
        }

        if (value == null) {
            throw new RuntimeException("Property '" + key + "' is not defined in any source.");
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }
}