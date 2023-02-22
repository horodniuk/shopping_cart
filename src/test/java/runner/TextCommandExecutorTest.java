package runner;

import cart.Cart;
import cart.Product;
import discount.Discount;
import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class TextCommandExecutorTest {

    TextCommandExecutor textCommandExecutor;
    Cart cart;
    Storage storage;

    @BeforeEach
    void beforeEachTestMethod() throws URISyntaxException {
        File path = new File(getClass().getClassLoader().getResource("storage.json").toURI());
        storage = new StorageWithJson(path);
        cart = new Cart(storage);
        textCommandExecutor = new TextCommandExecutor();
    }

    @ParameterizedTest
    @CsvSource({
            "add beer 10,beer",
            "add cola 3,cola",
            "add soap 3,soap"
    })
    void executeCommand_ifCommandAdd(String line, String expectedResult) {
        //Arrange
        //Act
        textCommandExecutor.executeCommand(line, cart);
        String actualResult = cart.getCartMap().keySet().stream().
                filter(product -> product.getName().equals(expectedResult)).findFirst().get().getName();
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ad beer 10", "add col 3", "add soap ", " ", ""})
    void executeCommand_ifCommandIncorrect(String line) {
        //Arrange
        //Act
        textCommandExecutor.executeCommand(line, cart);
        boolean actualResult = cart.getCartMap().keySet().isEmpty();
        //Assert
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @CsvSource({
            "add beer 10,remove beer 10,beer",
            "add cola 3,remove cola 3,cola",
            "add soap 3,remove soap 3,soap"
    })
    void executeCommand_ifCommandRemove(String addLine, String removeLine, String productName) {
        //Arrange
        textCommandExecutor.executeCommand(addLine, cart);
        //Act
        textCommandExecutor.executeCommand(removeLine, cart);
        boolean result = cart.getCartMap().keySet().stream().anyMatch(product -> product.getName().equals(productName));
        //Assert
        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({"add beer 10,discount buy_3_get_1_free beer,beer"})
    void executeCommand_ifCommandDiscount_Buy3Get1Free(String addLine, String discountLine, String productName) {
        //Arrange
        textCommandExecutor.executeCommand(addLine, cart);
        textCommandExecutor.executeCommand(discountLine, cart);
        Discount discount = new Discount_buy_3_get_1_free();
        //Act
        cart.applyDiscount(discount, productName);
        Product product = cart.getCartMap().keySet().stream().filter(p -> p.getName().equals(productName))
                .findFirst().get();
        boolean actualResult = cart.getDiscountStorage().isDiscountAppliedOnProduct(product);
        //Assert
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @CsvSource({"add cola 3,discount buy_1_get_30_percentage cola,cola"})
    void executeCommand_ifCommandDiscount_Buy1Get30Percent(String addLine, String discountLine, String productName) {
        //Arrange
        textCommandExecutor.executeCommand(addLine, cart);
        textCommandExecutor.executeCommand(discountLine, cart);
        Discount discount = new Discount_buy_1_get_30_percent_off();
        //Act
        cart.applyDiscount(discount, productName);
        Product product = cart.getCartMap().keySet().stream().filter(p -> p.getName().equals(productName))
                .findFirst().get();
        boolean actualResult = cart.getDiscountStorage().isDiscountAppliedOnProduct(product);
        //Assert
        assertTrue(actualResult);
    }

}