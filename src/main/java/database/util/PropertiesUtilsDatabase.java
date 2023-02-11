package database.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtilsDatabase {
    public static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtilsDatabase.class.getClassLoader().
                getResourceAsStream("database.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PropertiesUtilsDatabase() {
    }
}
