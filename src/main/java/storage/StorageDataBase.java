package storage;

import cart.Product;

import java.util.List;

public abstract class StorageDataBase implements Storage {

    @Override
    public void load() {
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

    public String getConnectionType() {
        return null;
    }
}
