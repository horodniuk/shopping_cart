import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AppByJsonStorageTest {
    private static final String TEST_ROOT_PATH_RESOURCES = "src/test/resources/";

    private static Stream<Arguments> checkPath() {
        return Stream.of(
                arguments("market_test storage_test.json", true),                      // corrected - folder(market_test) and file(storage_test.json) exist
                arguments("market_test storage_test.json commadsList_test.txt", true), // corrected - folder(market_test) and file(storage_test.json) and file(commadsList_test.txt) exist
                arguments("market storagejson", false),                                // not corrected - path not exist
                arguments("market storage", false),                                    // not corrected - path not exist
                arguments("market", false),                                            // not corrected - path not exist
                arguments("commandsList.txt", false),                                  // not corrected - path not exist
                arguments("", false)                                                   // not corrected - path not exist
        );
    }

    // Check the path for existence
    // Whe have two correct path for test:
    // 1) src/test/resources/market_test/storage_test.json
    // 2) src/test/resources/market_test/storage_test.json
    // and src/test/resources/market_test/commadsList_test.txt
    @ParameterizedTest
    @MethodSource("checkPath")
    void checkCorrectedPath(String line, boolean result) {
        boolean expected = result;
        String[] strArray = line.split(" ");
        boolean actual = isPathCorrect(strArray[0], strArray);
        assertEquals(expected, actual);
    }

    private static boolean isPathCorrect(String folder, String... files) {
        if (files.length <= 1) return false;
        boolean isPathExists = isDirectoryPathExist(folder);
        for (int i = 1; i < files.length; i++) {
            isPathExists = isFilePathExist(folder + "/" + files[i]);
            if (!isPathExists) return false;
        }
        return isPathExists;
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
