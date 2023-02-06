package storage;

import cart.Product;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Ignore
public class StorageWithJsonTest {
   /* String name;
    BigDecimal price;
    int quantity;

    @BeforeEach
    void init() {
        name = "bear";
        price = BigDecimal.valueOf(50.0);
        quantity = 30;
    }


    @Test
    void testLoad_whenFileIsParsed() throws IOException {
        //Arrange
        URI file = URI.create("src/test/resources/storage.json");
        Product product = new Product(name, price, quantity);
        Map<String, Product> expectedMap = new HashMap<>();
        expectedMap.put(product.getName(), product);
        StorageWithJson storageWithJson = new StorageWithJson(file);

        //Act
        Map<String, Product> actualMap = storageWithJson.load();
        //Assert
        assertEquals(expectedMap.containsKey("bear"), actualMap.containsKey("bear"));
    }

    @Test
    void testWrite() {
        //Arrange
        List<Product> productList = new LinkedList<>();
        productList.add(new Product("bear", BigDecimal.valueOf(10),20));
        productList.add(new Product("soap", BigDecimal.valueOf(20),10));
        productList.add(new Product("paper", BigDecimal.valueOf(10),40));

        Map<String,Product> productMap = new HashMap<>();
        productMap.put(productList.get(0).getName(),productList.get(0));
        productMap.put(productList.get(1).getName(),productList.get(1));
        productMap.put(productList.get(2).getName(),productList.get(2));
        //Act

        //Assert

    }
    */

}
