import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AppByJsonStorageTest {

    @Test
    void startTest() {
        AppByJsonStorage appByJsonStorage=mock(AppByJsonStorage.class);
        appByJsonStorage.start();
        verify(appByJsonStorage).start();
    }
}