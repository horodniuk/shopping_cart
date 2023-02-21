package runner;

import cart.Cart;
import cart.ConsoleCommandParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.Storage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TextCommandExecutorTest {

    TextCommandExecutor textCommandExecutor;
    @Mock
    Cart cart;
    @Mock
    Storage storage;
    ConsoleCommandParser consoleCommandParser;


    @BeforeEach
    void beforeEachTestMethod() {
        textCommandExecutor = new TextCommandExecutor();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"add beer 5",
                    "remove cola 3", "discount buy_3_get_1_free beer",
                    "discount buy_1_get_30_percentage cola", "remove beer 10", "finish", "price"
            })
    void executeCommand_ifOptionalIsPresent(String line) {
        when(cart.getStorage()).thenReturn(storage);
        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));
        consoleCommandParser = new ConsoleCommandParser(cart);
        boolean result = textCommandExecutor.executeCommand(line, cart);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"add bear 5", "remov cola 3", "discount buy_3_get_1_fre beer",
                    "discount buy_1_get_30 cola", "remove beer ", "finis", "pric", ""})
    void executeCommand_ifOptionalIsEmpty(String line) {
        when(cart.getStorage()).thenReturn(storage);
        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));
        consoleCommandParser = new ConsoleCommandParser(cart);
        boolean result = textCommandExecutor.executeCommand(line, cart);
        assertFalse(result);
    }
}