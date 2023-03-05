package config;

import cart.Product;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
@UtilityClass
public class HibernateSession {

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory;
        try {
            Configuration configuration = buildConfiguration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            String messageError = "Couldn't get session factory. {}";
            log.error(messageError, e.getMessage());
            throw new RuntimeException(messageError + e.getMessage());
        }
        return sessionFactory;
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Product.class);
        return configuration;
    }
}
