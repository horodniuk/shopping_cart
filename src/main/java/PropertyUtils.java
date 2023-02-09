import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    public static final Properties PROPERTIES = new Properties();
    private static final String FILENAME = "app.properties";

    static {
        readProperties();
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
                throw new RuntimeException("Could not find property file: [" + FILENAME + "].");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file: [" + FILENAME + "].", e);
        }
    }

}
