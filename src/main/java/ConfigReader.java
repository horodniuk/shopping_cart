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
     * next in try catch scope we creae FileReader
     * execute commands which we get after parsing the string of command.
     * First we create instance of class ConsoleCommandParser;
     * next we get Optional of class Command with the help of method parse()
     * Then checking if Optional of Command is empty or not.
     * If it's empty then we print message to console that user entered unknown command,
     * because we couldn't parse it with method parse()
     * IF Optional of Command is not empty: then we get command from instance of Optional ParsedCommand
     * and assign to a variable command;
     * after that we start method execute() which executes specified command.
     * method parse() - parses the received line (from file or from console);
     * method execute() - executes specified command.
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
            } else System.out.println("Wrong DB type specified in file app.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dbType;
    }
}
