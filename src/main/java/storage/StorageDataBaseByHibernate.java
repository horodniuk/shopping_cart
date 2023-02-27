package storage;

import cart.Product;
import config.HibernateSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class StorageDataBaseByHibernate extends StorageDataBase {
    @Getter
    private final String connectionType = "by_hibernate";
    @Getter
    private Map<Product, Integer> storageCache = new HashMap<>();
    private final String sqlQueryForProduct = "SELECT * FROM store";


    @Override
    public void load() {
        Session session = HibernateSession.getSessionFactory().openSession();
        List<Object[]> products = session.createSQLQuery(sqlQueryForProduct).list();
        for (Object[] product : products) {
            int id = (int) product[0];
            String productName = (String) product[1];
            BigDecimal price = (BigDecimal) product[2];
            Integer quantity = (Integer) product[3];
            storageCache.put(new Product(id, productName, price), quantity);
        }
        session.close();
    }

    @Override
    public void write() {

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

    @Override
    public boolean isProductAvailable(Product product, int quantity) {
        final var qetQuantityProductInStorage = getQuantity(product);
        if (qetQuantityProductInStorage < quantity) {
            System.out.printf("Storage doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                    product.getName(), quantity, qetQuantityProductInStorage);
        }
        return qetQuantityProductInStorage >= quantity;
    }

    private int getQuantity(Product product) {
        return storageCache.get(product);
    }

    @Override
    public Product getProductByName(String productName) {
        return storageCache.keySet().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow();
    }
}
