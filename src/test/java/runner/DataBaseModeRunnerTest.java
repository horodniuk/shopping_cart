package runner;

import config.ConfigReader;
import config.DbType;
import config.PropertyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class DataBaseModeRunnerTest {
    DataBaseModeRunner dataBaseModeRunner;
    ConfigReader configReader;

    @BeforeEach
    void init() {
        dataBaseModeRunner = new DataBaseModeRunner();
        configReader = new ConfigReader();
        PropertyUtils.PROPERTIES.clear();
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                arguments("by_hibernate", DbType.STORAGE_JSON),
                arguments("by_jdbc", DbType.STORAGE_DATABASE),
                arguments("some_type", null, IllegalArgumentException.class),
                arguments("12345678", null, IllegalArgumentException.class),
                arguments("JSON_STORAGE", null, IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void start(String connectionType) {
        by_hibernate
    }
}