package storage;

import cart.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class StorageDataBase implements Storage {
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
    public BigDecimal getProductPrice(Product product) {
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
