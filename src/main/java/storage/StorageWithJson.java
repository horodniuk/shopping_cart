package storage;

import cart.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Realisation of storage with products based on json file
 */
public class StorageWithJson implements Storage {
    private URI path;
    private Map<Product, Integer> storageCache;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageWithJson(URI path) {
        this.path = path;
        this.storageCache = load();
    }

    /*
     * Task (completed): rewrite method load() with checks and write tests for it
     * in the end we must get a Map with data? which are stored in resources by address
     * source root --> storage.json
     */
    @Override
    public Map<Product, Integer> load() {
        File jsonFile = new File(path.getPath());
        Map<Product, Integer> productMap = new LinkedHashMap<>();
        try {
            List<Product> productList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            productList.forEach(product -> productMap.put(product, product.getProduct_id()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return productMap;
    }

    /*
     * This task must be clarified
     * Task: implement method write() with checks and write tests for it
     * data from storage Map must be written in file, which is stored in resources by address
     * source root --> storage.json
     */
    @Override
    public void write() {
//        File jsonFile = new File(path);
        File jsonFile = new File("src/main/resources/test.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, storageCache.keySet());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) + quantity);
    }


    @Override
    public Product getProductByName(String productName) {
        return storageCache.keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow();
    }

    @Override
    public void removeProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) - quantity);
    }

   /* @Override
    public void reserveProduct(Product product, int quantity) {

    }*/

    /**
     * Task (completed): implement method checkProductAndQuantityInStorage
     * checking if product with this name exists in storage
     * check availability in storage, if available then return true,
     * if not - then we output to console, message that there is not enough quantity - and return false
     */
    @Override
    public boolean isProductAvailable(Product product, int quantity) {
        final var qetQuantityProductInStorage = getQuantity(product);
        if (qetQuantityProductInStorage < quantity) {
            System.out.printf("Storage doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                    product.getName(), quantity, qetQuantityProductInStorage);
        }
        return qetQuantityProductInStorage >= quantity;
    }


    /*
     * get return quantity(value) product in storage
     */
    private int getQuantity(Product product) {
        return storageCache.get(product);
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

    @Override
    public String toString() {
        return "StorageWithJson{" +
                "storageCache=" + storageCache +
                '}';
    }
}