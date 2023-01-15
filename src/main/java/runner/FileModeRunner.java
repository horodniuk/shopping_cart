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
        StorageWithJson storage = new StorageWithJson(pathToStorage);
        Cart cart = new Cart(storage);
        TextModeRunner textModeRunner = new TextModeRunner();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(pathToCommand));
            String line = reader.readLine();
            while (line != null) {
                if (line.length() > 0) textModeRunner.executeCommand(line, cart, storage);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathToCommand() {
        return pathToCommand;
    }
}
