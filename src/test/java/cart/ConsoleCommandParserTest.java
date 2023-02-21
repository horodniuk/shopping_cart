package cart;

import discount.Discount;
import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.Storage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleCommandParserTest {

    ConsoleCommandParser consoleCommandParser;
    @Mock
    Cart cart;
    @Mock
    Storage storage;

    @ParameterizedTest
    @ValueSource(strings = {
            "add beer 5",
            "remove cola 3",
            "discount buy_3_get_1_free beer",
            "discount buy_1_get_30_percentage cola",
            "remove beer 10",
            "finish",
            "price"
    })
    void testParse_ifOptionalIsPresent(String line) {
        //Arrange
        when(cart.getStorage()).thenReturn(storage);
        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));
        consoleCommandParser = new ConsoleCommandParser(cart);
        //Act
        Optional<CommandService> actualResult = consoleCommandParser.parse(line);
        //Assert
        assertTrue(actualResult.isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "add beer 5,ADD",
            "remove cola 3,REMOVE",
            "discount buy_3_get_1_free beer,DISCOUNT",
            "discount buy_1_get_30_percentage cola,DISCOUNT",
            "remove beer 10,REMOVE",
            "finish,FINISH",
            "price,PRICE"
    })
    void testParse_ifCommandEqualsExpectedCommand(String line, String expectedResult) {
        //Arrange
        when(cart.getStorage()).thenReturn(storage);
        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));
        consoleCommandParser = new ConsoleCommandParser(cart);
        //Act
        Optional<CommandService> commandService = consoleCommandParser.parse(line);
        String actualResult = commandService.get().getCommand().toString();
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "add bear 5",
            "remov cola 3",
            "discount buy_3_get_1_fre beer",
            "discount buy_1_get_30 cola",
            "remove beer ",
            "finis",
            "pric",
            ""
    })
    void testParse_ifCommandIsNotParsed(String line) {
        //Arrange
        when(cart.getStorage()).thenReturn(storage);
        when(storage.getProductNames()).thenReturn(List.of("beer", "cola", "soap"));
        consoleCommandParser = new ConsoleCommandParser(cart);
        //Act
        Optional<CommandService> actualResult = consoleCommandParser.parse(line);
        //Assert
        assertFalse(actualResult.isPresent());
    }

    @Test
    void parseDiscount_ifDiscountIs_buy_3_get_1_free() {
        //Arrange
        String line = "buy_3_get_1_free";
        Class<Discount_buy_3_get_1_free> expectedResult = Discount_buy_3_get_1_free.class;
        //Act
        Discount actualResult = ConsoleCommandParser.parseDiscount(line);
        //Assert
        assertEquals(expectedResult, actualResult.getClass());
    }

    @Test
    void parseDiscount_ifDiscountIs_buy_1_get_30_percent_off() {
        //Arrange
        String line = "buy_1_get_30_percentage";
        Class<Discount_buy_1_get_30_percent_off> expectedResult = Discount_buy_1_get_30_percent_off.class;
        //Act
        Discount actualResult = ConsoleCommandParser.parseDiscount(line);
        //Assert
        assertEquals(expectedResult, actualResult.getClass());
    }
}