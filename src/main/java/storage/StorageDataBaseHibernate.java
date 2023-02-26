package storage;

import cart.Product;
import config.HibernateSession;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StorageDataBaseHibernate implements Storage {
    private Map<Product, Integer> storageCache;

    public StorageDataBaseHibernate() {
        this.storageCache = load();            // filling map with method load()
    }

    @Override
    public Map<Product, Integer> load() {
        Session session = HibernateSession.getSessionFactory().openSession();
session.


        Transaction transaction = session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root root = criteriaQuery.from(Product.class);
        CriteriaQuery all = criteriaQuery.select(root);
        TypedQuery typedQuery = session.createQuery(all);
        return typedQuery.п();

        return null;
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
