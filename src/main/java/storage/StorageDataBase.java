package storage;

import cart.Product;

import java.util.List;
import java.util.Map;

public abstract class StorageDataBase implements Storage {

    /**
     * map storing products and their quantity which are loaded from database
     */
    private Map<Product, Integer> storageCache;

    @Override
    public Map<Product, Integer> load() {
        return null;
    }

    @Override
    public void write() {
    }

    @Override
    public void addProduct(Product product, int quantity) {
    }

    @Override
    public void removeProduct(Product product, int quantity) {
    }

    @Override
    public List<String> getProductNames() {
        return null;
    }

    @Override
    public boolean isProductAvailable(Product product, int quantity) {
        return false;
    }

    @Override
    public Product getProductByName(String productName) {
        return null;
    }
}
