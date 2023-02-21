import org.junit.jupiter.api.Test;
import runner.DataBaseModeRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class AppByDataBaseStorageTest {

    @Test()
    void startTest() {
        DataBaseModeRunner dataBaseModeRunner = mock(DataBaseModeRunner.class);
        dataBaseModeRunner.start();
        verify(dataBaseModeRunner).start();
    }
}