package runner;
// запускаем метод старт указываем файл

public class FileModeRunner implements ModeRunner {
   private String pathToStorage;
   private String pathToCommand;

    public FileModeRunner(String pathToStorage, String pathToCommand) {
        this.pathToStorage = pathToStorage;
        this.pathToCommand = pathToCommand;
    }

    @Override
    public void start() {
        System.out.println("Запускаем File mode");
    }
}
