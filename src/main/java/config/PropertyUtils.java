package config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
@Slf4j
public class PropertyUtils {
    public static final Properties PROPERTIES = new Properties();
    private static final String FILENAME = "config.properties";

    static {
        readProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Method description
     * This methode is made to read properties from file;
     * we crate inputStream with constant FILENAME;
     * next we check if inputStream equals null;
     * if true - we throw RuntimeException;
     * next we load properties with inputStream;
     * if we are not able to load properties from file - we throw RuntimeException;
     */
    private static void readProperties() {
        try (var inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(FILENAME)) {
            if (inputStream == null) {
                String messageError = "Could not find property file:{}";
                log.error(messageError,FILENAME);
                throw new RuntimeException( messageError +" [ "+ FILENAME +" ] " );
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            String messageError = "Could not read properties from file:";
            log.error(messageError,FILENAME,e.getMessage());
            throw new RuntimeException(messageError + " [" + FILENAME + "].", e);
        }
    }

}
