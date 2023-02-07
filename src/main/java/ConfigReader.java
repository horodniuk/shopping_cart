import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigReader {
    Path path;

    public ConfigReader(Path path) {
        this.path = path;
    }

    public DbType parse() {
        DbType dbType = null;
        try (FileReader reader = new FileReader(path.toFile())) {
            Properties properties = new Properties();
            properties.load(reader);
            String stringDBType = properties.getProperty("DB_TYPE");
            if (stringDBType.toUpperCase().equals(DbType.STORAGE_JSON.name())) {
                dbType = DbType.STORAGE_JSON;
            } else if (stringDBType.toUpperCase().equals(DbType.STORAGE_DATABASE.name())) {
                dbType = DbType.STORAGE_DATABASE;
            } else System.out.println("Wrong DB type specified in file app.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dbType;
    }
}
