package cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CommandServiceTest {

    CommandService commandService;
    Cart cart;
    Storage storage;

    @BeforeEach
    void beforeEachTestMethod() throws URISyntaxException {
        File path = new File(getClass().getClassLoader().getResource("test_storage.json").toURI());
        storage = new StorageWithJson(path);
        cart = new Cart(storage);
    }

    private void addProductBeer(String add, String beer, String quantity) {
        commandService = new CommandService(List.of(add, beer, quantity), Commands.ADD);
        commandService.addProductCommand(cart);
    }

    @ParameterizedTest
    @CsvSource({
            "add,beer,5",
            "add,cola,3",
            "add,soap,10"
    })
    void addProductCommand_IfArgumentsAreCorrect(String command, String productName, String quantity) {
        //Arrange
        addProductBeer(command, productName, quantity);
        String actualResult = cart.getCartMap().keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().get().getName();
        //Assert
        assertEquals(productName, actualResult);
    }

    @ParameterizedTest
    @CsvSource({
            "add,bear,10",
            "add,ola,3",
            "add,soape,10"
    })
    void addProductCommand_IfProductNameIsIncorrect(String command, String productName, String quantity) {
        //Arrange
        commandService = new CommandService(List.of(command, productName, quantity), Commands.REMOVE);
        //Act & Assert
        assertThrows(NoSuchElementException.class, () -> commandService.addProductCommand(cart));
    }

    @ParameterizedTest
    @CsvSource({
            "add,beer,5",
            "add,cola,3",
            "add,soap,10"
    })
    void removeProductCommand_IfArgumentsAreCorrect(String command, String productName, String quantity) {
        //Arrange
        addProductBeer(command, productName, quantity);
        //Act
        commandService.removeProductCommand(cart);
        boolean result = cart.getCartMap().keySet().stream().anyMatch(product -> product.getName().equals(productName));
        //Assert
        assertFalse(result);
    }

    @Test
    void removeProductCommand_IfProductNameIsIncorrect() {
        //Arrange
        addProductBeer("add", "beer", "10");
        commandService = new CommandService(List.of("add", "bearr", "10"), Commands.ADD);
        //Act & Assert
        assertThrows(NoSuchElementException.class, () -> commandService.addProductCommand(cart));
    }

    @Test
    void applyDiscountCommand_IfArgumentsAreCorrect_discountBuy3Get1Free() {
        //Arrange
        addProductBeer("add", "beer", "5");
        commandService = new CommandService(List.of("discount", "buy_3_get_1_free", "beer"), Commands.DISCOUNT);
        //Act
        commandService.applyDiscountCommand(cart);
        Product product = cart.getCartMap().keySet().stream().filter(p -> p.getName().equals("beer"))
                .findFirst().get();
        boolean actualResult = cart.getDiscountStorage().isDiscountAppliedOnProduct(product);
        //Assert
        assertTrue(actualResult);
    }

    @Test
    void applyDiscountCommand_IfArgumentsAreCorrect_discountBuy1Get30Percent() {
        //Arrange
        addProductBeer("add", "beer", "5");
        commandService = new CommandService(List.of("discount", "buy_1_get_30_percentage", "beer"), Commands.DISCOUNT);
        //Act
        commandService.applyDiscountCommand(cart);
        Product product = cart.getCartMap().keySet().stream().filter(p -> p.getName().equals("beer"))
                .findFirst().get();
        boolean actualResult = cart.getDiscountStorage().isDiscountAppliedOnProduct(product);
        //Assert
        assertTrue(actualResult);
    }

    @Test
    void applyDiscountCommand_IfProductNameIsIncorrect_discountBuy3Get1Free() {
        //Arrange
        addProductBeer("add", "beer", "5");
        commandService = new CommandService(List.of("discount", "buy_3_get_1_free", "bear"), Commands.DISCOUNT);
        //Act & Assert
        assertThrows(NoSuchElementException.class, () -> commandService.applyDiscountCommand(cart));
    }

    @Test
    void applyDiscountCommand_IfDiscountIsIncorrect_discountBuy3Get1Free() {
        //Arrange
        addProductBeer("add", "beer", "5");
        commandService = new CommandService(List.of("discount", "buy_3_get_1", "beer"), Commands.DISCOUNT);
        //Act & Assert
        assertThrows(NoSuchElementException.class, () -> commandService.applyDiscountCommand(cart));
    }
}