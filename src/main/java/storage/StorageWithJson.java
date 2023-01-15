package storage;

import cart.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Realisation of storage with products based on json file
 */
public class StorageWithJson implements Storage {
    private String file;
    private Map<String, Product> storageProducts;
    private File jsonFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageWithJson(String file) {
        this.file = file;
        this.storageProducts = load(file);
    }

    /*
     * Task (completed): rewrite method load() with checks and write tests for it
     * in the end we must get a Map with data? which are stored in resources by address
     * source root --> shopping_products_storage.json
     */
    @Override
    public Map<String, Product> load(String file) {
        jsonFile = new File(file);
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
        jsonFile = new File(file);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, storage.values());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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