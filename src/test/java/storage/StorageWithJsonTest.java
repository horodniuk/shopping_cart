package storage;

import cart.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StorageWithJsonTest {
    File path;
    Storage storage;

    @BeforeEach
    void beforeEachTestMethod() throws URISyntaxException {
        path = new File(getClass().getClassLoader().getResource("test_storage.json").toURI());
        storage = new StorageWithJson(path);
        storage.load();
    }

    private static Stream<Arguments> getProductAndQuantity() {
        return Stream.of(
                arguments(1, "beer", BigDecimal.valueOf(50.0), 5),
                arguments(2, "cola", BigDecimal.valueOf(20.0), 5),
                arguments(3, "soap", BigDecimal.valueOf(30.0), 5)
        );
    }

    @ParameterizedTest
    @MethodSource("getProductAndQuantity")
    void addProduct(int product_id, String productName, BigDecimal price, Integer quantity) {
        //Arrange
        Product product = new Product(product_id, productName, price);
        int quantityNeeded = 105;
        //Act
        storage.addProduct(product, quantity);
        boolean result = storage.isProductAvailable(product, quantityNeeded);
        //Assert
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"beer", "cola", "soap"})
    void getProductByName_IfProductExistInStorage(String productName) {
        //Arrange
        //Act
        Product product = storage.getProductByName(productName);
        String actualResult = product.getName();
        //Assert
        assertEquals(productName, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bear", "apple", "water"})
    void getProductByName_IfProductNotExistInStorage(String productName) {
        //Arrange
        //Act & Assert
        assertThrows(NoSuchElementException.class, () -> storage.getProductByName(productName));
    }

    @ParameterizedTest
    @MethodSource("getProductAndQuantity")
    void removeProduct(int product_id, String productName, BigDecimal price, Integer quantity) {
        //Arrange
        Product product = new Product(product_id, productName, price);
        int quantityNeeded = 95;
        //Act
        storage.removeProduct(product, quantity);
        boolean result = storage.isProductAvailable(product, quantityNeeded);
        //Assert
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("getProductAndQuantity")
    void isProductAvailable_whenResultTrue(int product_id, String productName, BigDecimal price, Integer quantity) {
        //Arrange
        Product product = new Product(product_id, productName, price);
        boolean expectedResult = true;
        //Act
        boolean actualResult = storage.isProductAvailable(product, quantity);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1,beer,50.0,500",
            "2,cola,20.0,115",
            "3,soap,30.0,200"
    })
    void isProductAvailable_whenResultFalse(int product_id, String productName, BigDecimal price, Integer quantity) {
        //Arrange
        Product product = new Product(product_id, productName, price);
        boolean expectedResult = false;
        //Act
        boolean actualResult = storage.isProductAvailable(product, quantity);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getProductNames() {
        //Arrange
        List<String> expectedResult = List.of("beer", "cola", "soap");
        //Act
        List<String> actualResult = storage.getProductNames();
        //Assert
        assertEquals(expectedResult, actualResult);
    }
}