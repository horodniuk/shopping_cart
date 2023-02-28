package config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigReader {
    private final static String CORE_CONFIGURATION_DB_TYPE = "storage_type";

    /**
     * Method description
     * This methode is made to parse data from file of properties;
     * we return DBType (enum) by getting it constant PROPERTIES using String CORE_CONFIGURATION_DB_TYPE;
     * if we are not able to get it from properties - we throw IllegalArgumentException
     * ("Wrong DB type specified in file config.properties!");
     */
    public DbType parseConfig() {
        try {
            return DbType.valueOf(PropertyUtils.PROPERTIES.getProperty(CORE_CONFIGURATION_DB_TYPE));
        } catch (IllegalArgumentException e) {
            String messageError = "Error when trying to read from file: {}.";
            log.error(messageError ,e.getMessage());
            throw new IllegalArgumentException(messageError + e.getMessage());

        }
    }
}
