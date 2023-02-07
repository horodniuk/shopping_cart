public class DataSourceManager {
    ConfigReader configReader;

    public DataSourceManager(ConfigReader configReader) {
        this.configReader = configReader;
    }

    /**
     * Method description
     * This methode is made to start app depending on db type specified in properties file;
     * First we create variable dbType and assign it with DBType with method - parse();
     * next in switch case, we start AppByJsonStorage.start() or AppByDataBaseStorage.start() depending on value of
     * DBType;
     */
    void start() {
        DbType dbType = configReader.parse();
        switch (dbType) {
            case STORAGE_JSON -> AppByJsonStorage.start();
            case STORAGE_DATABASE -> AppByDataBaseStorage.start();
        }
    }
}
