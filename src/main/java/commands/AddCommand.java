package commands;

import cart.Cart;
import cart.Product;

import java.util.List;

public class AddCommand extends Command {

    private String product;
    private int quantity;

    /**
     * Method receiveArguments() - receives arguments (strings) in method parse() in class ConsoleCommandParser.
     * In List <String> newArguments: argument(0) - is command, argument (1) - productName, argument (2) - quantity.
     */
    public void receiveArguments(List<String> newArguments) {
        product = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }

    /**
     * Method execute() - executes command. In this case - command - add, which adds product in
     * given quantity in cart Map.
     * parameters - instance of class Cart.
     */
    public void execute(Cart cart) {
        cart.add(product, quantity);
    }

}
