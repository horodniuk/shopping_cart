import lombok.extern.slf4j.Slf4j;
import runner.DataBaseModeRunner;
@Slf4j
public class AppByDataBaseStorage {

    /**
     * Method description
     *
     */
    static void start() {
        log.info("Start DataBase method.");
        new DataBaseModeRunner().start();
    };
}
