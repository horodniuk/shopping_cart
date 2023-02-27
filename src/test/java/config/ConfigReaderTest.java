package config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class ConfigReaderTest {

    ConfigReader configReader;

    @BeforeEach
    void init() {
        configReader = new ConfigReader();
        PropertyUtils.PROPERTIES.clear();
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                arguments("STORAGE_JSON", DbType.STORAGE_JSON, null),
                arguments("STORAGE_DATABASE", DbType.STORAGE_DATABASE, null),
                arguments("SOME_STORAGE", null, IllegalArgumentException.class),
                arguments("12345678", null, IllegalArgumentException.class),
                arguments("JSON_STORAGE", null, IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void parseConfig(String dbType, DbType expectedResult, Class<Exception> expectedException) {
        //Arrange
        PropertyUtils.PROPERTIES.setProperty("storage_type", dbType);
        //Act & Assert
        if (expectedException != null) {
            Assertions.assertThrows(expectedException, () -> configReader.parseConfig());
        } else {
            DbType actualResult = configReader.parseConfig();
            Assertions.assertEquals(expectedResult, actualResult);

        }
    }
}