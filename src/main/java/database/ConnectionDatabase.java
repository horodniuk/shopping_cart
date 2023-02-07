package database;

import database.util.Connector;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    public static void main(String[] args) throws SQLException {
        try (var connection = Connector.open()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
