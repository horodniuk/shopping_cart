import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import runner.FileModeRunner;
import runner.InteractiveModeRunner;
import runner.ModeRunner;
import storage.Storage;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AppByJsonStorageTest {
    private static final String TEST_ROOT_PATH_RESOURCES = "src/test/resources/";
    Map<String, ModeRunner> map = new HashMap<>();
    Storage storage;
    File file;

    @BeforeEach
    void init() throws URISyntaxException {
        file = new File(getClass().getClassLoader().getResource("test_storage.json").toURI());
        map.put("market_test storage_test.json", new InteractiveModeRunner(storage));
        map.put("market_test storage_test.json commadsList_test.txt", new FileModeRunner(storage, file));
    }

    private static Stream<Arguments> addMarketPath() {
        return Stream.of(
                arguments("market storage.json", true),
                arguments("market storage.json commadsList.txt", true),
                arguments("market storagejson", false),
                arguments("market storage", false),
                arguments("market", false),
                arguments("commandsList.txt", false),
                arguments("", false)
        );
    }

    @ParameterizedTest
    @MethodSource("addMarketPath")
    void checkMarket(String query, boolean result) {
        boolean expected = result;
        Optional<ModeRunner> s = Optional.ofNullable(map.get(query));
        boolean actual = s.isPresent();
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> aPath() {
        return Stream.of(
                arguments("market_test storage_test.json", true), //first arg-enter console, second arg-we check valid
                arguments("market_test storage_test.json commadsList_test.txt", true),
                arguments("market storage.xml", false),
                arguments("market storage", false),
                arguments("market", false),
                arguments("commandsList.txt", false),
                arguments("", false)
        );
    }

    @ParameterizedTest
    @MethodSource("aPath")
    void checkMarketPath(String line, boolean result) {
        boolean expected = result;
        String[] strArray = line.split(" ");
        boolean actual = isPathCorrect(strArray[0], strArray);
        assertEquals(expected, actual);
    }

    private static boolean isPathCorrect(String folder, String... files) {
        boolean isCorrect = files.length > 1;
        if (!isCorrect) return false;
        isCorrect = isDirectoryPathExist(folder);
        for (int i = 1; i < files.length; i++) {
            isCorrect = isFilePathExist(folder + "/" + files[i]);
            if (!isCorrect) return false;
        }
        return isCorrect;
    }

    private static boolean isFilePathExist(String path) {
        File file = new File(TEST_ROOT_PATH_RESOURCES + path);
        return file.exists();
    }

    private static boolean isDirectoryPathExist(String path) {
        File file = new File(TEST_ROOT_PATH_RESOURCES + path);
        return file.isDirectory();
    }
}
