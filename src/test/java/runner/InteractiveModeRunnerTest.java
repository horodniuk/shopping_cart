package runner;

import cart.Cart;
import cart.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InteractiveModeRunnerTest {
    InteractiveModeRunner interactiveModeRunner;
    File path;
    Storage storage;

    @BeforeEach
    void beforeEachTestMethod() throws URISyntaxException {
        path = new File(getClass().getClassLoader().getResource("test_storage.json").toURI());
        storage = new StorageWithJson(path);
        interactiveModeRunner = new InteractiveModeRunner(storage);
    }

    @Test
    void startExpectedProductBeer() {
        String expected = "beer";
        Product product = storage.getProductByName(expected);
        String actual = product.getName();
        assertEquals(expected, actual);
    }

    @Test
    void startExpectedProductCola() {
        String expected = "cola";
        Product product = storage.getProductByName(expected);
        String actual = product.getName();
        assertEquals(expected, actual);
    }

    @Test()
    void textCommandExecutorVerify() {
        Cart cart = mock(Cart.class);
        TextCommandExecutor textCommandExecutor = mock(TextCommandExecutor.class);
        textCommandExecutor.executeCommand("finish", cart);
        verify(textCommandExecutor).executeCommand("finish", cart);
    }

}