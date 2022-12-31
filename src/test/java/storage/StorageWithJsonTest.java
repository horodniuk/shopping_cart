package storage;

import cart.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StorageWithJsonTest {
    String name;
    BigDecimal price;
    int quantity;

    @BeforeEach
    void init() {
        name = "bear";
        price = BigDecimal.valueOf(50.0);
        quantity = 30;
    }


    @Test
    void testLoad_whenFileIsParsed() {
        //Arrange
        File file = new File("src/main/resources/shopping_products_storage.json");
        Product product = new Product(name, price, quantity);
        Map<String, Product> expectedMap = new HashMap<>();
        expectedMap.put(product.getName(), product);
        StorageWithJson storageWithJson = new StorageWithJson(file);

        //Act
        Map<String, Product> actualMap = storageWithJson.load(file);
        //Assert
        assertEquals(expectedMap.containsKey("bear"), actualMap.containsKey("bear"));
    }
}
