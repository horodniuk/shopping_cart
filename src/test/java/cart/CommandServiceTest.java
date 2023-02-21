package cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.Storage;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandServiceTest {



    CommandService commandService;
    @Mock
    Cart cart;
    @Mock
    ConsoleCommandParser consoleCommandParser;
    @Mock
    Storage storage;

    @ParameterizedTest
    @ValueSource(strings = {"add beer 5","add cola 10", "add soap 5"})
    void addProductCommand(String line) {
        //Arrange
        when(cart.getStorage()).thenReturn(storage);
        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));


//        when (consoleCommandParser.parse(line)).
//        when(cart.add()).thenReturn()
//        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));
//        consoleCommandParser = new ConsoleCommandParser(cart);

        //Act
        commandService.addProductCommand(cart);
        //Assert

    }

    @Test
    void removeProductCommand() {
    }

    @Test
    void applyDiscountCommand() {
    }

    @Test
    void finishCommand() {
    }

    @Test
    void priceCommand() {
    }
}