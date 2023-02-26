import config.ConfigReader;
import config.DbType;

public class DataSourceManager {
    ConfigReader configReader;

    public DataSourceManager(ConfigReader configReader) {
        this.configReader = configReader;
    }

    /**
     * Method description
     * This methode is made to start app depending on db type specified in properties file;
     * We create variable dbType and assign it value with method - parse();
     * next in switch case, we start config.AppByJsonStorage.start() or config.AppByDataBaseStorage.start() depending on
     * value of DBType;
     */
    public void start() {
        DbType dbType = configReader.parseConfig();
        switch (dbType) {
            case STORAGE_JSON -> AppByJsonStorage.start();
            case STORAGE_DATABASE_JDBC -> AppByDataBaseStorage.start();
            case STORAGE_DATABASE_HIBERNATE -> AppByDataBaseStorageHibernate.start();
        }
    }
}

