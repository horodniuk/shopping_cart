package runner;

import cart.Cart;
import storage.StorageWithJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reading commands line by line from file and if such commands exist -
 * perform them. (look method executeCommand())
 */
public class FileModeRunner implements ModeRunner {
    private String pathToStorage;
    private String pathToCommand;

    public FileModeRunner(String pathToStorage, String pathToCommand) {
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
        TextModeRunner textModeRunner = new TextModeRunner();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToCommand))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.length() > 0) textModeRunner.executeCommand(line, cart);
                line = reader.readLine();
            }
            cart.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathToCommand() {
        return pathToCommand;
    }
}
