package database;

import cart.Product;
import database.util.Connector;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionDatabase {
    public static void main(String[] args) throws SQLException {
        Map<Product, Integer> productListDataBase;
        String sql = """
                        SELECT product_id, product_name, product_price, product_quantity
                        FROM storage_database.product.product
                """;

        try (var connection = Connector.open();
             var statement = connection.createStatement()) {
            var executeResult = statement.executeQuery(sql);
            productListDataBase = new HashMap<>();
            while (executeResult.next()) {
                productListDataBase.put(new Product(executeResult.getInt("product_id"),
                                executeResult.getString("product_name"),
                                executeResult.getBigDecimal("product_price")),
                        executeResult.getInt("product_quantity"));
            }
            System.out.println(productListDataBase);
        }
    }
}

