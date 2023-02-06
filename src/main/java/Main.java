import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(Path.of("/"));
        DataSourceManager dataSourceManager = new DataSourceManager(configReader);
        dataSourceManager.start();
    }
}
