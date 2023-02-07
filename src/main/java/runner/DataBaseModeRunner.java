package runner;

public class DataBaseModeRunner implements ModeRunner {
    @Override
    public void start() {
        System.out.println("starting app from db storage");
    }
}
