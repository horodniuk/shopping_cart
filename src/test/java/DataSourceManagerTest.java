import config.ConfigReader;
import config.DbType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataSourceManagerTest {

    ConfigReader configReader = new ConfigReader();


    @Test
    void startExpectedJson() {
        DbType actual = configReader.parseConfig();
        DbType expected = DbType.STORAGE_JSON;
        assertEquals(expected, actual);
    }
}