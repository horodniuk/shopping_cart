package storage;

import cart.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Realisation of storage with products based on json file
 */
public class StorageWithJson implements Storage {
    private String path;
    private Map<String, Product> storageProducts;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageWithJson(String path) {
        this.path = path;
        this.storageProducts = load(path);
    }

    /*
     * Task (completed): rewrite method load() with checks and write tests for it
     * in the end we must get a Map with data? which are stored in resources by address
     * source root --> shopping_products_storage.json
     */
    @Override
    public Map<String, Product> load(String file) {
        File jsonFile = new File(path);
        Map<String, Product> productMap = new LinkedHashMap<>();
        try {
            List<Product> productList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            productList.forEach(product -> productMap.put(product.getName(), product));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return productMap;
    }

    /*
     * This task must be clarified
     * Task: implement method write() with checks and write tests for it
     * data from storage Map must be written in file, which is stored in resources by address
     * source root --> shopping_products_storage.json
     */
    @Override
    public void write(Map<String, Product> storage) {
        File jsonFile = new File(path);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, storage.values());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product, int quantity) {
        if (storageProducts.containsKey(product.getName())) {
            storageProducts.get(product.getName()).setQuantity(storageProducts.get(product.getName()).
                    getQuantity() + quantity);
        } else storageProducts.put(product.getName(), product);
    }

    @Override
    public void removeProduct(String productName, int quantity) {
        if (storageProducts.get(productName).getQuantity() > quantity) {
            storageProducts.get(productName).setQuantity(storageProducts.get(productName).getQuantity() - quantity);
        } else storageProducts.remove(productName);
    }

    @Override
    public void reserveProduct(Product product, int quantity) {

    }

    /**
     * Task (completed): implement method checkProductAndQuantityInStorage
     * checking if product with this name exists in storage
     * check availability in storage, if available then return true,
     * if not - then we output to console, message that there is not enough quantity - and return false
     *
     * @return
     */
    public boolean isProductAvailable(String productName, int quantity) {
        if (getStorageMap().get(productName).getQuantity() < quantity) {
            System.out.printf("Storage doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                    productName, quantity, getStorageMap().get(productName).getQuantity());
        }
        return getStorageMap().get(productName).getQuantity() >= quantity;
    }

    @Override
    public List<String> getProductNames() {
        return storageProducts.keySet().stream().toList();
    }

    @Override
    public BigDecimal getProductPrice(String productName) {
        return storageProducts.get(productName).getPrice();
    }

    @Override
    public Map<String, Product> getStorageMap() {
        return storageProducts;
    }

    @Override
    public String toString() {
        return "StorageWithJson{" +
                "storageProducts=" + storageProducts +
                '}';
    }
}