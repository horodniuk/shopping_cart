import java.util.Optional;

public class DataSourceManager {
    ConfigReader configReader;

    public DataSourceManager(ConfigReader configReader) {
        this.configReader = configReader;
    }

    /**
     * Method description
     * This methode is made to start app depending on db type specified in properties file;
     * We create variable dbType which is optional of Class DBType and assign it value with method - parse();
     * First we check if optional of DBType is empty.
     * if true - we throw IllegalArgumentException;
     * if false - next in switch case, we start AppByJsonStorage.start() or AppByDataBaseStorage.start() depending on
     * value of DBType;
     */
    void start() {
        Optional<DbType> dbType = configReader.parse();
        if (dbType.isEmpty()) {
            throw new IllegalArgumentException("Wrong DB type specified in file app.properties!");
        } else {
            switch (dbType.get()) {
                case STORAGE_JSON -> AppByJsonStorage.start();
                case STORAGE_DATABASE -> AppByDataBaseStorage.start();
            }
        }
    }
}
