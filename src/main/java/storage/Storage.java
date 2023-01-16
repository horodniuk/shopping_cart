package storage;

import cart.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/*
 * Method load() loads data in our Map
 * if we parse file json - then we get container Map with filled with data from this file,
 * if we parse file with connecting to database - it will have its own realisation,
 * but in the end we will also get Map filled from database (not sure for now)
 *
 * Method write() - writes updated data to the file
 * Method getStorage() - getter of Map with data
 */
public interface Storage {
    Map<String, Product> load();

    void write(Map<String, Product> storage);

    void addProduct(Product product);

    void removeProduct(Product product);

    void reserveProduct(Product product, int quantity);

    void updateQuantityProductsInStorage(String productName, int quantity);

    List<String> getProductNames();

    BigDecimal getProductPrice(String productName);

    Map<String, Product> getStorageMap();
}
