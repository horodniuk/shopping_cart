package commands;

import cart.Cart;

import java.util.List;

public class RemoveCommand extends Command {

    private String productName; // name of product
    private int quantity; // product quantity

    /**
     * Method receiveArguments() - receives arguments (strings) in method parse() in class ConsoleCommandParser.
     * In List <String> newArguments: argument(0) - is command, argument (1) - productName, argument (2) - quantity.
     */
    public void receiveArguments(List<String> newArguments) {
        productName = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }
    /**
     * Method execute() - executes command. In this case - command - remove, which removes product in
     * given quantity from cart Map.
     * parameters - instance of class Cart.
     */
    public void execute(Cart cart) {
        cart.remove(productName, quantity);
    }
}
