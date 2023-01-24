package storage;

import cart.Product;

import java.io.IOException;
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
 * Method addProduct() - add product to the storage map;
 * Method removeProduct() - removes product from the storage map;
 * Method getProductNames() - returns the names of all the products from the Storage map;
 * Method isProductAvailable() - checks if storage map contains needed quantity of such product;
 * Method getProductPrice() - returns the price of the product from storage map;
 */
public interface Storage {
    Map<String, Product> load() throws IOException;

    void write() throws IOException;

    void addProduct(Product product, int quantity);

    void removeProduct(String productName, int quantity);

    List<String> getProductNames();

    BigDecimal getProductPrice(String productName);

    boolean isProductAvailable(String productName, int quantity);
}
