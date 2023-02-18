import config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        LOGGER.info("Test work logger");
        ConfigReader configReader = new ConfigReader();
        DataSourceManager dataSourceManager = new DataSourceManager(configReader);
        dataSourceManager.start();
    }
}
