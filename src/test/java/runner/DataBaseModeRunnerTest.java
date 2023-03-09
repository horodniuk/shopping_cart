package runner;

import config.PropertyUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class DataBaseModeRunnerTest {
    DataBaseModeRunner dataBaseModeRunner;

    @BeforeEach
    void init() {
        dataBaseModeRunner = new DataBaseModeRunner();
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                arguments("some_type", RuntimeException.class),
                arguments("12345678", RuntimeException.class),
                arguments("abc", RuntimeException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void start_IfConnectionTypeIsIncorrect(String connectionType, Class<Exception> expectedException) {
        //Arrange
        PropertyUtils.PROPERTIES.clear();
        PropertyUtils.PROPERTIES.setProperty("db.connection_type", connectionType);
        //Act & Assert
        Assertions.assertThrows(expectedException, () -> dataBaseModeRunner.start());
    }
}