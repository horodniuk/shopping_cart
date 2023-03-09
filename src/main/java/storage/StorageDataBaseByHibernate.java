package storage;

import cart.Product;
import config.HibernateSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@ToString(of = {"storageCache"})
@Getter
public class StorageDataBaseByHibernate extends StorageDataBase {

    private final String connectionType = "by_hibernate";
    private Map<Product, Integer> storageCache = new HashMap<>();

    @Override
    public void load() {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            List<Object[]> products = session.createSQLQuery("SELECT * FROM store").list();
            for (Object[] product : products) {
                int id = (int) product[0];
                String productName = (String) product[1];
                BigDecimal price = (BigDecimal) product[2];
                Integer quantity = (Integer) product[3];
                storageCache.put(new Product(id, productName, price), quantity);
            }
        } catch (HibernateException e) {
            String errorMessage = "Couldn't get data from sql table with hibernate! {}";
            log.error(errorMessage, e.getMessage());
            throw new RuntimeException(errorMessage + e.getMessage());
        }
    }

    //method not ready
    @Override
    public void write() {
//        try (Session session = HibernateSession.getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            for (Product product : storageCache.keySet()) {
//                Integer quantity = storageCache.get(product);
//                int id = product.getProduct_id();
//                session.createQuery("UPDATE product SET quantity =:quantity where id = :id")
//                        .setParameter("quantity", quantity)
//                        .setParameter("id", id)
//                        .executeUpdate();
//                session.getTransaction().commit();
//            }
//            session.close();
//        } catch (HibernateException e) {
//            String errorMessage = "Couldn't update data in sql table with hibernate! {}";
//            log.error(errorMessage, e.getMessage());
//            throw new RuntimeException(errorMessage + e.getMessage());
//        }
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
