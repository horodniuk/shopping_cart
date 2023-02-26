package config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static config.Connector.open;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectorTest {



    @Test
    public void testOpenConnection() throws SQLException {



        String testUrl = "jdbc:postgresql://localhost:3306/storage";

        String testUser = "testuser";

        String testPassword = "testpass";

        PropertyUtils.PROPERTIES.setProperty("url", testUrl);
        PropertyUtils.PROPERTIES.setProperty("user", testUser);
        PropertyUtils.PROPERTIES.setProperty("password", testPassword);


        Connection connection = open();

        assertNotNull(connection);
        connection.close();
    }

@Test
public void testOpenConnectionWithInvalidUrl() throws RuntimeException {
        PropertyUtils.PROPERTIES.setProperty("url", "jdbc:postgresql://localhost:3306/storage");
        open();
        }
}
