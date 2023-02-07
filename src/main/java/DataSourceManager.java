public class DataSourceManager {
    ConfigReader configReader;

    public DataSourceManager(ConfigReader configReader) {
        this.configReader = configReader;
    }

    void start() {
        DbType dbType = configReader.parse();
        switch (dbType) {
            case STORAGE_JSON -> AppByJsonStorage.start();
            case STORAGE_DATABASE -> AppByDataBaseStorage.start();
        }
    }
}
