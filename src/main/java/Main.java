public class Main {
    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(Main.class.getResourceAsStream("app.properties"));
        DataSourceManager dataSourceManager = new DataSourceManager(configReader);
        dataSourceManager.start();
    }
}
