package runner;

import cart.Cart;
import storage.StorageWithJson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

/**
 * Reading commands line by line from file and if such commands exist -
 * perform them. (look method executeCommand())
 */
public class FileModeRunner implements ModeRunner {
    private URI pathToStorage;
    private URI pathToCommand;

    public FileModeRunner(URI pathToStorage, URI pathToCommand) {
        this.pathToStorage = pathToStorage;
        this.pathToCommand = pathToCommand;
    }

    /**
     * Reading commands line by line from file.
     */
    @Override
    public void start() {
        System.out.println("Starting File mode." + " Commands will be read from file\" " + pathToCommand);
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        TextCommandExecutor textCommandExecutor = new TextCommandExecutor();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(pathToCommand)))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.length() > 0) textCommandExecutor.executeCommand(line, cart);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cart.finish();
    }

    public URI getPathToCommand() {
        return pathToCommand;
    }
}
