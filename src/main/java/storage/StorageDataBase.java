package storage;

import cart.Product;
import database.ConnectionDatabase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Realisation of storage with products based from database
 */
public class StorageDataBase implements Storage {
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
        return new ConnectionDatabase().extractedProductFromDataBase();
    }

    @Override
    public void write() {

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
