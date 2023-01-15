package runner;

import cart.Cart;
import cart.CartCommandParser;
import storage.Storage;
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
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(pathToCommand));
            String line = reader.readLine();
            while (line != null) {
                if (line.length() > 0) executeCommand(line, cart, storage);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Task (completed)
     * We must make method which will be performing commands, which are included in technical task
     * For example: If we get String "add beer 5" we must parse it to get
     * name of product "beer" and quantity "5" and check if we have such product,
     * in such quantity and after perform the command.
     * for example:
     * add beer 5 --> cart.add("beer", 5)
     * add soap 2 --> cart.add("soap", 2)
     * discount buy_1_get_30_percentage beer --> applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), "beer")
     * discount buy_3_get_1_free soap --> applyDiscount(new Discount_BUY_3_GET_1_FREE(), "soap")
     *
     * test list with commands is located in resources by address source root --> commandsList.txt
     */
    @Override
    public void executeCommand(String line, Cart cart, Storage storage) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        if (cartCommandParser.parse(line, storage)) return;
        if ((line.equals("price"))) {
            cart.price();
        } else {
            System.out.println("unknown command - " + line);
        }
    }

    public String getPathToCommand() {
        return pathToCommand;
    }
}
