package storage;

import cart.Product;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public abstract class StorageDataBase implements Storage {
    @Getter
    private final String connectionType = "type";
    @Getter
    private Map<Product, Integer> storageCache;

    @Override
    public abstract void load();

    @Override
    public abstract void write();

    @Override
    public abstract void addProduct(Product product, int quantity);

    @Override
    public abstract void removeProduct(Product product, int quantity);

    @Override
    public abstract List<String> getProductNames();

    @Override
    public abstract boolean isProductAvailable(Product product, int quantity);

    @Override
    public abstract Product getProductByName(String productName);
}
