package storage;

import cart.Product;

import java.util.List;

/*
 * Method load() loads data in our Map
 * if we parse file json - then we get container Map with filled with data from this file,
 * if we parse file with connecting to database - it will have its own realisation,
 * but in the end we will also get Map filled from database (not sure for now)
 *
 * Method write() - writes updated data to the file or database
 * Method addProduct() - adds product to the storage map;
 * Method removeProduct() - removes product from the storage map;
 * Method getProductNames() - returns the names of all the products from the Storage map;
 * Method getProductPrice() - returns the price of the specified product from storage map;
 * Method isProductAvailable() - checks if storage map contains needed quantity of such product;
 */
public interface Storage {
    void load();

    void write();

    void addProduct(Product product, int quantity);

    void removeProduct(Product product, int quantity);

    List<String> getProductNames();

    boolean isProductAvailable(Product product, int quantity);

    Product getProductByName(String productName);
}
