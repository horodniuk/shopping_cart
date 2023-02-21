import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class AppByJsonStorageTest {
/*
    @TempDir
    Path tempDir;

    @Test
    void testStartWithInteractiveMode() throws IOException {
        File storageFile = new File(tempDir.toFile(), "storage.json");
        assertTrue(storageFile.createNewFile());
        byte[] input = "market storage.json\nexit\n".getBytes();
        System.setIn(new ByteArrayInputStream(input));
        assertDoesNotThrow(() -> AppByJsonStorage.start());
    }

    @Test
    void testStartWithFileMode() throws IOException {
        File storageFile = new File(tempDir.toFile(), "storage.json");
        assertTrue(storageFile.createNewFile());
        File commandsFile = new File(tempDir.toFile(), "commands.txt");
        assertTrue(commandsFile.createNewFile());
        byte[] input = "market storage.json commands.txt\nexit\n".getBytes();
        System.setIn(new ByteArrayInputStream(input));
        assertDoesNotThrow(() -> AppByJsonStorage.start());
    }

    @Test
    void testStartWithInvalidCommand() {
        byte[] input = "invalid command\nexit\n".getBytes();
        System.setIn(new ByteArrayInputStream(input));
        assertDoesNotThrow(() -> AppByJsonStorage.start());
    }

    @Test
    void testGetAccessToFileByCopy() throws IOException {
        String fileName = "storage.json";
        File file = new File(tempDir.toFile(), fileName);
        assertTrue(file.createNewFile());
        String path = fileName;
        File resultFile = AppByJsonStorage.getAccessToFileByCopy(path);
        assertTrue(resultFile.exists());
    }

    @Test
    void testIsFilePathExist() throws IOException {
        String fileName = "storage.json";
        File file = new File(tempDir.toFile(), fileName);
        assertTrue(file.createNewFile());
        assertTrue(AppByJsonStorage.isFilePathExist(tempDir.toString() + File.separator + fileName));
    }

    @Test
    void testIsDirectoryPathExist() {
        assertTrue(AppByJsonStorage.isDirectoryPathExist(tempDir.toString()));
    }

    @Test
    void testIsPathCorrectWithValidFiles() throws IOException {
        String fileName1 = "storage.json";
        String fileName2 = "commands.txt";
        File file1 = new File(tempDir.toFile(), fileName1);
        File file2 = new File(tempDir.toFile(), fileName2);
        assertTrue(file1.createNewFile());
        assertTrue(file2.createNewFile());
        assertTrue(AppByJsonStorage.isPathCorrect(tempDir.toString(), fileName1, fileName2));
    }

    @Test
    void testIsPathCorrectWithInvalidFiles() {
        String fileName = "invalid_file.json";
        assertThrows(IllegalArgumentException.class, () -> AppByJsonStorage.isPathCorrect(tempDir.toString(), fileName));
    }

    @Test
    void testGetPath() {
        String fileName = "storage.json";
        assertNotNull(AppByJsonStorage.getPath(File.separator + fileName));
    }

    @Test
    void testGetLineToConsole() {
        String input = "market storage.json\nexit\n";
        System.setIn(input.getBytes());
        assertEquals("market storage.json", AppByJsonStorage.getLineToConsole());
    }*/
}
