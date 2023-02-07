package database;

import database.util.Connector;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionDatabase {
    public static void main(String[] args) throws SQLException {
        String sql="""
                SELECT *
                FROM company_storage.products

        """;

        try (var connection = Connector.open();
             var statement = connection.createStatement()) {

            var executeResult = statement.executeQuery(sql);
            Map<String,BigDecimal> list=new HashMap<>();
            while (executeResult.next()){
                System.out.print(executeResult.getString("name")+" price: ");
                System.out.print(executeResult.getBigDecimal("price")+" quantity: ");
                System.out.print(executeResult.getInt("quantity"));
                System.out.println("");
                list.put(executeResult.getString("name"),
                        executeResult.getBigDecimal("price") );
            }
        }
    }
}
