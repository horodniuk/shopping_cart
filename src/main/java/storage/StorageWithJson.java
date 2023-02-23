package storage;

import cart.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Realisation of storage with products based on json file
 */
@Slf4j
@ToString(of = {"storageCache"})
public class StorageWithJson implements Storage {
    private File path; // path in which json file is situated;
    private Map<Product, Integer> storageCache; // map storing products and their quantity which are loaded from json;
    private final ObjectMapper objectMapper = new ObjectMapper(); // instance of class ObjectMapper

    public StorageWithJson(File path) {
        this.path = path;
        this.storageCache = load();  // filling map with method load()
    }

    /**
     * Method description
     * First we create empty Map for containing instances of class Product and Integers (theirs quantity);
     * then we create instance of class JsonNode, which contains all the data from json file (which we read with method
     * readTree();
     * next we create another JsonNode to get to level of node storage;
     * then get data for each product, and it's quantity and fill map productMap;
     * return - map of instances of class Product and Integers (theirs quantity).
     */
    @Override
    public Map<Product, Integer> load() {
        Map<Product, Integer> productMap = new HashMap<>();
        try {
            System.out.println("SRR " + path);
            JsonNode jsonNode = objectMapper.readTree(path);
            JsonNode storage = jsonNode.get("storage");
            for (JsonNode node : storage) {
                int product_id = Integer.parseInt(node.get("product_id").asText());
                String name = node.get("name").asText();
                BigDecimal price = new BigDecimal(node.get("price").asText());
                int quantity = Integer.parseInt(node.get("quantity").asText());
                Product tempProduct = new Product(product_id, name, price);
                productMap.put(tempProduct, quantity);
            }
        } catch (IOException exception) {
            log.error("The path or file is invalid or missing : ",exception);
            exception.printStackTrace();
        }
        return productMap;
    }

    /**
     * Method description
     * Writes changes from storageCache to our jsonFile;
     * First we create empty Map for containing instances of class Product and Integers (theirs quantity);
     * then we create instance of class JsonNode, which contains all the data from json file (which we read with method
     * readTree();
     * then we check if product id equals product id from json file. If true - then we rewrite quantity of this product
     * from storageCache to json file;
     */
    @Override
    public void write() {
        File tempStorage = new File("temp/temp_storage.json");
        try {
            JsonNode jsonNode = objectMapper.readTree(path);
            JsonNode storage = jsonNode.get("storage");
            for (JsonNode node : storage) {
                for (Product product : storageCache.keySet()) {
                    if (node.get("product_id").asInt() == (product.getProduct_id())) {
                        ((ObjectNode) node).put("quantity", storageCache.get(product));
                    }
                }
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(tempStorage, jsonNode);
        } catch (IOException e) {
            log.error("The path or file is invalid or missing : ",e);
            e.printStackTrace();
        }
    }

    /**
     * Method description
     * parameters - instance of class Product, quantity if this product;
     * method changes quantity (increases) of this product in storageCache map.
     */
    @Override
    public void addProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) + quantity);
    }

    /**
     * Method description
     * parameters - string name of the product;
     * method returns instance of class Product found by its name.
     */
    @Override
    public Product getProductByName(String productName) {
        return storageCache.keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow();
    }

    /**
     * Method description
     * parameters - instance of class product, quantity if this product;
     * method changes quantity (decreases) of this product in storageCache map.
     */
    @Override
    public void removeProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) - quantity);
    }


    /**
     * Method description
     * parameters - instance of class Product, quantity if this product;
     * checks if specified product exists in needed quantity in storageCache map;
     * we check if quantity of product in storage is smaller than needed quantity,
     * if true: then we output message to console - that we don't have enough of this product in storage;
     * return - we return true if quantity of this product in storage is bigger or equals needed quantity,
     * otherwise we return false.
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


    /**
     * Method description
     * parameters - instance of class Product;
     * return - we return quantity of specified product in storageCache map
     */
    private int getQuantity(Product product) {
        return storageCache.get(product);
    }

    /**
     * Method description
     * return - we return all the names of products stored in storageCache map.
     */
    @Override
    public List<String> getProductNames() {
        return storageCache.keySet().stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }
}