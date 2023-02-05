
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

public class ConfigLoader {

    public String load(URI path) {
        Properties property = new Properties();

        try (FileReader fileReader = new FileReader(path.getPath())) {
            property.load(fileReader);
            if (property.getProperty("DB-TYPE").equals("STORAGE_JSON")) {
                return "STORAGE_JSON";
            } else return "STORAGE_DB";
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
