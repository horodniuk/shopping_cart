package database.util;

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
                    PropertiesUtilsDatabase.get(URL_KEY),
                    PropertiesUtilsDatabase.get(USER_KEY),
                    PropertiesUtilsDatabase.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
