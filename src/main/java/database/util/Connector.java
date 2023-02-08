package database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String PASSWORD = "postgres";
    private static final String USER = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/storage_database";

    private Connector() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
