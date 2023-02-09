import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final static String CORE_CONFIGURATION_DB_TYPE = "DB_TYPE";
    private Properties properties;


    public ConfigReader(String fileName) {
        properties = readProperties(fileName);
    }

    private Properties readProperties(String fileName) {
        Properties props = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new RuntimeException("Could not find property file: [" + fileName + "].");
        }
        try {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file: [" + fileName + "].", e);
        }
        return props;
    }

    public DbType parseConfig() {
        try {
            return DbType.valueOf(properties.getProperty(CORE_CONFIGURATION_DB_TYPE));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong DB type specified in file app.properties!");
        }
    }
}
