package storage;

import cart.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StorageDataBaseTest {

    Map<Product, Integer> storageCache;

    @BeforeEach
    void beforeEachTestMethod() {
        storageCache = new HashMap<>();
        storageCache.put(new Product(1, "beer", BigDecimal.valueOf(50.0)), 100);
        storageCache.put(new Product(2, "cola", BigDecimal.valueOf(20.0)), 100);
        storageCache.put(new Product(3, "soap", BigDecimal.valueOf(30.0)), 100);
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
        storageCache.put(product, storageCache.get(product) + quantity);
        final var qetQuantityProductInStorage = storageCache.get(product);
        boolean result = qetQuantityProductInStorage >= quantityNeeded;
        //Assert
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("getProductAndQuantity")
    void removeProduct(int product_id, String productName, BigDecimal price, Integer quantity) {
        //Arrange
        Product product = new Product(product_id, productName, price);
        int quantityNeeded = 95;
        //Act
        storageCache.put(product, storageCache.get(product) - quantity);
        final var qetQuantityProductInStorage = storageCache.get(product);
        boolean result = qetQuantityProductInStorage >= quantityNeeded;
        //Assert
        assertTrue(result);
    }

    @Test
    void getProductNames() {
        //Arrange
        List<String> expectedResult = List.of("beer", "cola", "soap");
        //Act
        List<String> actualResult = storageCache.keySet().stream()
                .map(Product::getName).sorted()
                .collect(Collectors.toList());
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("getProductAndQuantity")
    void isProductAvailable_whenResultTrue(int product_id, String productName, BigDecimal price, Integer quantity) {
        //Arrange
        Product product = new Product(product_id, productName, price);
        boolean expectedResult = true;
        //Act
        final var qetQuantityProductInStorage = storageCache.get(product);
        boolean actualResult = qetQuantityProductInStorage >= quantity;
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
        final var qetQuantityProductInStorage = storageCache.get(product);
        boolean actualResult = qetQuantityProductInStorage >= quantity;
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"beer", "cola", "soap"})
    void getProductByName_IfProductExistInStorage(String productName) {
        //Arrange
        //Act
        Product product = storageCache.keySet().stream().filter(p -> p.getName().equals(productName))
                .findFirst().orElseThrow();
        String actualResult = product.getName();
        //Assert
        assertEquals(productName, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bear", "apple", "water"})
    void getProductByName_IfProductNotExistInStorage(String productName) {
        //Arrange
        //Act & Assert
        assertThrows(NoSuchElementException.class, () -> storageCache.keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow());
    }
}