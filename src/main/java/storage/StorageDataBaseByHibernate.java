package storage;

import cart.Product;
import config.HibernateSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
    private String sqlQueryForProduct = "FROM Product";


    @Override
    public void load() {
        Session session = HibernateSession.getSessionFactory().openSession();
        Query query = session.createQuery(sqlQueryForProduct);
        List<Product> productList = (List<Product>) query.getResultList();

//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Product.class);
//        Root root = criteriaQuery.from(Product.class);
//        CriteriaQuery all = criteriaQuery.select(root);
//        TypedQuery typedQuery = session.createQuery(all);
//        List<Product> productList = typedQuery.getResultList();
        System.out.println(productList);
//                .stream().sorted().toList();
//        System.out.println(productList);
//        List<Integer> quantityList = session.createQuery("SELECT quantity FROM store").stream().sorted().toList();
//        System.out.println(quantityList);
//        Transaction transaction = session.beginTransaction();

//        CriteriaBuilder criteriaBuilder = session.createSQLQuery();
//
//
//        createQuery(Product.class);
//        Root root = criteriaQuery.from(Product.class);
//        CriteriaQuery all = criteriaQuery.select(root);
//        TypedQuery typedQuery = session.createQuery(all);
//        return typedQuery.п();
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
