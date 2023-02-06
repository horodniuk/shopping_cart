import java.nio.file.Path;
import java.util.Properties;

public class ConfigReader {
    Path path;

    public ConfigReader(Path path) {
        this.path = path;
    }

    Properties properties = new Properties();
    public DbType parse() {
        return DbType.STORAGE_JSON;
    }
}
