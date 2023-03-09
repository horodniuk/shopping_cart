package storage;

import cart.Product;
import config.HibernateSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class StorageDataBaseByHibernateTest {
    Map<Product, Integer> storageCache;

    @BeforeEach
    public void beforeTestMethod() {
        storageCache = new HashMap<>();
        storageCache.put(new Product(1, "beer", BigDecimal.valueOf(50.0)), 100);
        storageCache.put(new Product(2, "cola", BigDecimal.valueOf(20.0)), 100);
        storageCache.put(new Product(3, "soap", BigDecimal.valueOf(30.0)), 100);
    }

    @Test
    public void testHibernateConnection() {
        try (var sessionFactory = HibernateSession.getSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Product product : storageCache.keySet()) {
                session.save(product);
            }
            List<Product> products = session.createQuery("FROM Product", Product.class).list();
            System.out.println(products);
            session.getTransaction().commit();
        }
    }
}