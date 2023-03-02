package runner;

import cart.Cart;
import lombok.extern.slf4j.Slf4j;
import storage.Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reading commands line by line from file and if such commands exist -
 * perform them. (look method executeCommand())
 */
@Slf4j
public class FileModeRunner implements ModeRunner {
    private Storage storage;
    private File pathToCommand;

    public FileModeRunner(Storage storage, File pathToCommand) {
        this.storage = storage;
        this.pathToCommand = pathToCommand;
    }

    /**
     * Method description
     * starts reading and executing commands line by line from file;
     * First we create instance of Class TextCommandExecutor
     * after that we create BufferedReader instance and instance of class Cart;
     * next we read file line by line until line!=null;
     * each line we pass to method executeCommand() to execute each command;
     * after we read all lines we start method finish(), that finishes work of program.
     * method executeCommand() - executes command in line from file;
     * method finish() - finishes work of program and writes changes to Storage (if such occurred).
     */
    @Override
    public void start() {
        log.info("File mode started.");
        System.out.println("Starting File mode." + " Commands will be read from file\" " + pathToCommand);
        Cart cart = new Cart(storage);
        TextCommandExecutor textCommandExecutor = new TextCommandExecutor();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToCommand))) {
            String line = reader.readLine();
            log.debug("Program read line {} ",line);
            while (line != null) {
                if (line.length() > 0){
                    log.info("Program execute {} command.",line);
                    textCommandExecutor.executeCommand(line, cart);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            String messageError = "Error when trying to read from file: {}.";
            log.error(messageError,e.getMessage());
            throw new RuntimeException(messageError+e.getMessage());
        }
        cart.finish();
    }
}
