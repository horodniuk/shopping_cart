package runner;

import cart.Cart;
import cart.CartCommandParser;
import storage.StorageWithJson;

import java.util.Scanner;

/**
 * Reading commands line by line from console and if such commands exist -
 * perform them. (look method executeCommand())
 */
public class InteractiveModeRunner implements ModeRunner {
    private String pathToStorage;

    public InteractiveModeRunner(String pathToStorage) {
        this.pathToStorage = pathToStorage;
    }

    /**
     * Reading commands line by line from console.
     */
    public void start() {
        System.out.println("Starting Interactive mode.");
        showTooltipWithCommands();
        System.out.println("Enter the command in console:");
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        while (true) {
            String line = new Scanner(System.in).nextLine();
            if (line.equals("finish")) return;
            executeCommand(line, cart);
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
     */
    @Override
    public void executeCommand(String line, Cart cart) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        if (cartCommandParser.parse(line)) return;
        if (line.equals("price")) {
            cart.price();
        } else {
            System.out.println("Unknown command, try again, for example \"add beer 5\"");
        }
    }

    /**
     * Show instruction to Customer
     */
    private void showTooltipWithCommands() {
        System.out.println("Instruction:");
        System.out.println("\"price\" - show price of products in cart");
        System.out.println("\"add beer 5\" - add product in cart. Structure: add [product name] [product quantity]");
        System.out.println("\"discount buy_1_get_30_percentage beer\" - apply discount. " +
                "Structure: discount [discount name] [product name]");
        System.out.println("\"discount buy_3_get_1_free beer\" - apply discount. " +
                "Structure: discount [discount name] [product name]");
        System.out.println("\"finish\" - finish work");
    }
}
