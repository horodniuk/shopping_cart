package config;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ConfigReaderTest {

    ConfigReader configReader = new ConfigReader();
    DbType result = configReader.parseConfig() ;

    @Before
    public void setup() {
        PropertyUtils.PROPERTIES.clear();
    }
    @Test
    public void testParseConfigWithValidDbType() {
        // Arrange
        PropertyUtils.PROPERTIES.setProperty("core.configuration.db.type", "FILE_NAME");



        assertNotNull(result);
        assertEquals(DbType.STORAGE_JSON, result);

    }

    @Test
    public void testParseConfigWithInvalidDbType() throws IllegalArgumentException {

        PropertyUtils.PROPERTIES.setProperty("core.configuration.db.type", "INVALID_DB_TYPE");

        configReader.parseConfig();
    }
    @Test
    public void testParseConfigWithMissingDbType() throws IllegalArgumentException  {
        configReader.parseConfig();
    }
}
