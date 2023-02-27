package config;

import cart.Product;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

@Slf4j
public class HibernateSession {
    private static SessionFactory sessionFactory;
    private static final String DRIVER = PropertyUtils.get("db.driver");
    private static final String URL = PropertyUtils.get("db.url");
    private static final String USER = PropertyUtils.get("db.username");
    private static final String PASSWORD = PropertyUtils.get("db.password");
    private static final String DIALECT = PropertyUtils.get("db.dialect");
    private static final String SHOW_SQL = PropertyUtils.get("db.show_sql");
    private static final String HBM2DDL_AUTO = PropertyUtils.get("db.hbm2ddl_auto");

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, DRIVER);
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, DIALECT);
                properties.put(Environment.SHOW_SQL, SHOW_SQL);
                properties.put(Environment.HBM2DDL_AUTO, HBM2DDL_AUTO);

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(Product.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                        applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                String messageError = "Couldn't get session factory. {}";
                log.error(messageError, e.getMessage());
                throw new RuntimeException(messageError + e.getMessage());
            }
        }
        return sessionFactory;
    }
}
