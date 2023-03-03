package runner;

import cart.Product;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileModeRunnerTest {

    File incorrectPathToCommands = new File(" ");


    FileModeRunner fileModeRunner;
    Storage storageReal;
    @Mock
    Storage storage;

    @Test
    void start_IfMethodThrowsRuntimeException() {
        //Arrange
        fileModeRunner = new FileModeRunner(storage, incorrectPathToCommands);
        //Act & Assert
        Assert.assertThrows(RuntimeException.class, () -> fileModeRunner.start());
    }

    @Test
    void start_IfMethodWorks() throws URISyntaxException {
        //Arrange
        File path = new File(getClass().getClassLoader().getResource("test_storage.json").toURI());
        String expectedResult = "beer";
        storageReal = new StorageWithJson(path);
        storageReal.load();
        File correctPathToCommands = new File(getClass().getClassLoader().
                getResource("commandsList.txt").toURI());
        //Act
        fileModeRunner = new FileModeRunner(storage, correctPathToCommands);
        Product product = storageReal.getProductByName(expectedResult);
        String actualResult = product.getName();
        //Assert
        assertEquals(expectedResult,actualResult);
    }
}