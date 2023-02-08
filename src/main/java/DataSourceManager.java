public class DataSourceManager {
    ConfigReader configReader;

    public DataSourceManager(ConfigReader configReader) {
        this.configReader = configReader;
    }

    void start (){
        AppByDataBaseStorage.start();
    /*    DbType dbType = configReader.parse();
        if (dbType.name().equals("STORAGE_JSON")){*/
            //AppByJsonStorage.start();
       /* }
        if (dbType.name().equals("STORAGE_DATABASE")){
            AppByDataBaseStorage.start();                     ////------DATABASE
        }*/
    };
}
