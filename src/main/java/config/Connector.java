package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String PASSWORD_KEY = "db.password";
    private static final String USER_KEY = "db.username";
    private static final String URL_KEY = "db.url";

    private Connector() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertyUtils.get(URL_KEY),
                    PropertyUtils.get(USER_KEY),
                    PropertyUtils.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
