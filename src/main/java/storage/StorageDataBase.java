package storage;

import cart.Product;
import database.util.Connector;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Realisation of storage with products based from database
 */
public class StorageDataBase implements Storage {
    private Connector connector;
    /**
     * map storing products and their quantity which are loaded from database
     */
    private Map<Product, Integer> storageCache;

    public StorageDataBase() {
        this.storageCache = load();            /** filling map with method load() */
    }

    /**
     * Method description
     * First we create empty Map for containing instances of class Product and Integers (theirs quantity);
     * then we create instance of class ConnectionDatabase, which contains all the data from database
     * extractedProductFromDataBase() method extracted mapProduct from database get data for each product, and it's quantity;
     * return - map of instances of class Product and Integers (theirs quantity).
     */
    @Override
    public Map<Product, Integer> load() {
        Map<Product, Integer> productListDataBase;
        String sql = """
                        SELECT product_id, product_name, product_price, product_quantity
                        FROM storage_database.product.product
                """;

        try (var connection = connector.open();
             var statement = connection.createStatement()) {
            var executeResult = statement.executeQuery(sql);
            productListDataBase = new HashMap<>();
            while (executeResult.next()) {
                productListDataBase.put(new Product(executeResult.getInt("product_id"),
                                executeResult.getString("product_name"),
                                executeResult.getBigDecimal("product_price")),
                        executeResult.getInt("product_quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productListDataBase;
    }

    @Override
    public void write() {
        List<Integer> valueList = new ArrayList<>(storageCache.values());
        String sql = """
                        DROP TABLE IF EXISTS product.temp_product;
                        CREATE TABLE product.temp_product
                        (
                            product_id       SERIAL PRIMARY KEY,
                            product_name     VARCHAR(255),
                            product_price    DECIMAL NOT NULL,
                            product_quantity INT     NOT NULL
                        );
                        INSERT INTO product.temp_product(product_name,
                        product_price,product_quantity)
                        VALUES ('beer', 50.0, ?),
                               ('cola', 20.0, ?),
                               ('soap', 30.0, ?);
                """;
        try (var connection = connector.open();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setInt(1,valueList.get(0));
            prepareStatement.setInt(2,valueList.get(1));
            prepareStatement.setInt(3,valueList.get(2));

            var executeResult = prepareStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) + quantity);
    }

    @Override
    public void removeProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) - quantity);
    }

    @Override
    public List<String> getProductNames() {
        return storageCache.keySet().stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getProductPrice(Product product) {
        return product.getPrice();
    }

    private int getQuantity(Product product) {
        return storageCache.get(product);
    }

    @Override
    public boolean isProductAvailable(Product product, int quantity) {
        final var qetQuantityProductInStorage = getQuantity(product);
        if (qetQuantityProductInStorage < quantity) {
            System.out.printf("Storage doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                    product.getName(), quantity, qetQuantityProductInStorage);
        }
        return qetQuantityProductInStorage >= quantity;
    }

    @Override
    public Product getProductByName(String productName) {
        return storageCache.keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return "StorageDataBase{" +
               "storageCache=" + storageCache +
               '}';
    }
}
