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
     * we check if stream equals null;
     * if true - we throw IllegalArgumentException;
     * if false we go next;
     * next in try catch scope we create inputStreamReader to read properties file;
     * after that we create instance of class Properties and with its help load all properties from properties file;
     * we create stringDBType assign it value of property DB_TYPE;
     * next we start method getDBType();
     * return - DBType (Enum).
     */
    public DbType parse() {
        if (stream == null) {
            throw new IllegalArgumentException("Couldn't read file! Wrong path in stream!");
        } else {
            try (InputStreamReader reader = new InputStreamReader(stream)) {
                Properties properties = new Properties();
                properties.load(reader);
                String stringDBType = properties.getProperty("DB_TYPE");
                return getDBType(stringDBType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method description
     * This methode is made to get DBType (enum);
     * we announce variable dbType which is optional of DBType;
     * next we check if value of stringDBType equals any DbType.values();
     * if any equals - we reassign value of dbType to Optional of this DB (enum);
     * after that we check if Optional of DBType is empty;
     * if true we throw IllegalArgumentException;
     * else - we return  Optional of DBType (Enum).
     */
    private DbType getDBType(String stringDBType) {
        Optional<DbType> dbType = Optional.empty();
        for (DbType db : DbType.values()) {
            if (stringDBType.toUpperCase().equals(db.toString())) {
                dbType = Optional.of(db);
            }
        }
        if (dbType.isEmpty()) {
            throw new IllegalArgumentException("Wrong DB type specified in file app.properties!");
        } else {
            return dbType.get();
        }
    }
}
