import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class ConfigReader {
    InputStream stream;

    public ConfigReader(InputStream stream) {
        this.stream = stream;
    }

    /**
     * Method description
     * This methode is made to parse data from file of properties;
     * First we announce variable dbType;
     * next in try catch scope we create inputStreamReader to read properties file;
     * after that we create instance of class Properties and with its help load all properties from properties file;
     * we create stringDBType assign it value of property DB_TYPE;
     * next we check if value of stringDBType equals STORAGE_JSON;
     * if true - we reassign new value to DBType - STORAGE_JSON;
     * if false - we check next statement;
     * we check if value of stringDBType equals STORAGE_DATABASE;
     * if true - we reassign new value to DBType - STORAGE_JSON;
     * if false - we output message to console - that wrong dbType is specified in properties file.
     */
    public DbType parse() {
        DbType dbType = null;
        try (Reader reader = new InputStreamReader(stream)) {
            Properties properties = new Properties();
            properties.load(reader);
            String stringDBType = properties.getProperty("DB_TYPE");
            if (stringDBType.toUpperCase().equals(DbType.STORAGE_JSON.toString())) {
                dbType = DbType.STORAGE_JSON;
            } else if (stringDBType.toUpperCase().equals(DbType.STORAGE_DATABASE.toString())) {
                dbType = DbType.STORAGE_DATABASE;
            } else System.out.println("Wrong DB type specified in file app.properties!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dbType;
    }
}
