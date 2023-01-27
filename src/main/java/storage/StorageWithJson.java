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


/**
 * Realisation of storage with products based on json file
 */
public class StorageWithJson implements Storage {
    private URI path; // path in which json file is situated;
    private Map<String, Product> storageCache; // map storing products and their names which ar loaded from json file;
    private ObjectMapper objectMapper = new ObjectMapper(); // instance of class ObjectMapper

    public StorageWithJson(URI path) throws IOException {
        this.path = path;
        this.storageCache = load(); // filling map with method load()
    }

    /**
     * Method description
     * First we create the instance of class File (jsonFile), then creating empty Map for containing strings and
     * instances of class Product;
     * then we read values from jsonFile and write them in empty List of products;
     * after that, list of products is putted in map;
     * return - map of strings (product names as keys) and instances of class Product.
     */
    @Override
    public Map<String, Product> load() throws IOException {
        File jsonFile = new File(path.getPath());
        Map<String, Product> productMap = new LinkedHashMap<>();
        List<Product> productList = objectMapper.readValue(jsonFile, new TypeReference<>() {
        });
        productList.forEach(product -> productMap.put(product.getName(), product));
        return productMap;
    }

    /**
     * Method description
     * Writes changes from storageCache to our jsonFile;
     * First we create the instance of class File (jsonFile);
     * then we write values to jsonFile from map storageCache;
     */
    @Override
    public void write() throws IOException {
//        File jsonFile = new File(path);
        File jsonFile = new File("src/main/resources/test.json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, storageCache.values());

    }

    /**
     * Method description
     * parameters - instance of class Product, quantity if this product;
     * method changes quantity (increases) of this product in storageCache map.
     */
    @Override
    public void addProduct(Product product, int quantity) {
        storageCache.get(product.getName()).setQuantity(getQuantity(product.getName()) + quantity);
    }

    /**
     * Method description
     * parameters - string product name, quantity if this product;
     * method changes quantity (decreases) of this product in storageCache map.
     */
    @Override
    public void removeProduct(String productName, int quantity) {
        storageCache.get(productName).setQuantity(getQuantity(productName) - quantity);
    }

   /* @Override
    public void reserveProduct(Product product, int quantity) {

    }*/

    /**
     * Method description
     * parameters - string product name, quantity if this product;
     * checks if specified product exists in needed quantity in storageCache map;
     * we check if quantity of product in storage is smaller than needed quantity,
     * if true: then we output message to console - that we don't have enough of this product in storage;
     * return - we return true if quantity of this product in storage is bigger or equals needed quantity,
     * otherwise we return false.
     */
    @Override
    public boolean isProductAvailable(String productName, int quantity) {
        final var qetQuantityProductInStorage = getQuantity(productName);
        if (qetQuantityProductInStorage < quantity) {
            System.out.printf("Storage doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                    productName, quantity, qetQuantityProductInStorage);
        }
        return qetQuantityProductInStorage >= quantity;
    }

    /**
     * Method description
     * parameters - string product name;
     * return - we return quantity of specified product in storageCache map
     */
    private int getQuantity(String productName) {
        return storageCache.get(productName).getQuantity();
    }

    /**
     * Method description
     * return - we return all the names of products stored in storageCache map.
     */
    @Override
    public List<String> getProductNames() {
        return storageCache.keySet().stream().toList();
    }

    /**
     * Method description
     * parameters - string name of product
     * return - we return price of specified product from storageCache map.
     */
    @Override
    public BigDecimal getProductPrice(String productName) {
        return storageCache.get(productName).getPrice();
    }

    @Override
    public String toString() {
        return "StorageWithJson{" +
                "storageCache=" + storageCache +
                '}';
    }
}