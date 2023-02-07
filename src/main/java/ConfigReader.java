import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;

public class ConfigReader {
    InputStream stream;

    public ConfigReader(InputStream stream) {
        this.stream = stream;
    }

    /**
     * Method description
     * This methode is made to parse data from file of properties;
     * First we announce variable dbType which is optional of DBType;
     * next in try catch scope we create inputStreamReader to read properties file;
     * we check if reader is ready to be read;
     * after that we create instance of class Properties and with its help load all properties from properties file;
     * we create stringDBType assign it value of property DB_TYPE;
     * next we check if value of stringDBType equals STORAGE_JSON;
     * if true - we reassign new value to DBType - STORAGE_JSON;
     * if false - we check next statement;
     * we check if value of stringDBType equals STORAGE_DATABASE;
     * if true - we reassign new value to DBType - STORAGE_JSON;
     * if false - we output message to console - that wrong dbType is specified in properties file.
     * return - Optional of DBType (Enum).
     */
    public Optional<DbType> parse() {
        Optional<DbType> dbType = Optional.empty();
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            if (!reader.ready()) {
                throw new IllegalArgumentException("Couldn't read file! Wrong path in stream!");
            }
            Properties properties = new Properties();
            properties.load(reader);
            String stringDBType = properties.getProperty("DB_TYPE");
            for (DbType db : DbType.values()) {
                if (stringDBType.toUpperCase().equals(db.toString())) {
                    dbType = Optional.of(db);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dbType;
    }
}
