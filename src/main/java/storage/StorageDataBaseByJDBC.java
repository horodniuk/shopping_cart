package storage;

import cart.Product;
import config.Connector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Realisation of storage with products based from database
 */
@NoArgsConstructor
@ToString(of = {"storageCache"})
public class StorageDataBaseByJDBC extends StorageDataBase {
    @Getter
    private final String connectionType = "by_jdbc";
    private final String PRODUCT_ID = "product_id";
    private final String PRODUCT_NAME = "product_name";
    private final String PRICE = "price";
    private final String QUANTITY = "quantity";
    String sql = """
                    SELECT product_id, product_name, price, quantity
                    FROM storage_database.public.store
            """;

    @Getter
    private Map<Product, Integer> storageCache = new HashMap<>();

    /***
     * trying to populate the map from the database
     */
    @Override
    public void load() {
        try (var connection = Connector.open();
             var statement = connection.createStatement()) {
             var executeResult = statement.executeQuery(sql);
             while (executeResult.next()) {
                storageCache.put(
                        new Product(executeResult.getInt(PRODUCT_ID),
                                    executeResult.getString(PRODUCT_NAME),
                                    executeResult.getBigDecimal(PRICE)
                        ),
                        executeResult.getInt(QUANTITY));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * when program finish
     * trying to  the map from the database
     */
    @Override
    public void write() {
        List<String> queries = convertStoragetoQueries(storageCache);
        try (var connection = Connector.open();
             var statement = connection.createStatement()) {
            var executeResult = statement.executeQuery(sql);
            statement.addBatch("DROP TABLE IF EXISTS public.temp_store cascade");
            statement.addBatch("CREATE TABLE public.temp_store (LIKE public.store INCLUDING ALL)");
            for (String query : queries) {
                 statement.addBatch(query);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * convert storage to queries
     * @param storageCache
     * @return list queries
     */
    private List<String> convertStoragetoQueries(Map<Product, Integer> storageCache) {
        List<String> list = new ArrayList<>();
        storageCache.forEach((product, quantity) ->
                list.add(String.format("insert into temp_store (%s, %s, %s, %s) VALUES ( %s, '%s',  %s,  %s)",
                        PRODUCT_ID, PRODUCT_NAME, PRICE, QUANTITY,
                        product.getProduct_id(), product.getName(), product.getPrice().intValue(), quantity))
        );
        return list;
    }

    @Override
    public void addProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) + quantity);
    }

    @Override
    public void removeProduct(Product product, int quantity) {
        storageCache.put(product, storageCache.get(product) - quantity);
    }

    @Override
    public List<String> getProductNames() {
        return storageCache.keySet().stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    private int getQuantity(Product product) {
        return storageCache.get(product);
    }

    @Override
    public boolean isProductAvailable(Product product, int quantity) {
        final var qetQuantityProductInStorage = getQuantity(product);
        if (qetQuantityProductInStorage < quantity) {
            System.out.printf("Storage doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                    product.getName(), quantity, qetQuantityProductInStorage);
        }
        return qetQuantityProductInStorage >= quantity;
    }

    @Override
    public Product getProductByName(String productName) {
        return storageCache.keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow();
    }
}
