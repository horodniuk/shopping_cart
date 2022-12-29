package storage;

import cart.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StorageWithJson implements Storage {
    Map<String, Product> storageProducts;

    public StorageWithJson(String file) {
        this.storageProducts = load(file);
    }

    @Override
    public Map<String, Product> load(String file) {
        Map<String, Product> products = new HashMap<>();
        products.put("bear", new Product("bear", new BigDecimal(50.0), 30));
        products.put("cola", new Product("cola", new BigDecimal(20.0), 20));
        products.put("soap", new Product("soap", new BigDecimal(30.0), 10));
        // нужно заполнить products из файла json, сейчас заполнено вручную
        return products;
    }

    @Override
    public void write(Product product) {
        // записать продукт в файл json если
    }

    @Override
    public Map<String, Product> getStorage() {
        return storageProducts;
    }

    @Override
    public String toString() {
        return "StorageWithJson{" +
               "storageProducts=" + storageProducts +
               '}';
    }
}
