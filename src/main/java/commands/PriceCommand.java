package commands;

import cart.Cart;

import java.util.List;

public class PriceCommand extends Command {

    private List<String> arguments; // list of arguments that may be needed in future

    /**
     * Method receiveArguments() - receives arguments (strings) in method parse() in class ConsoleCommandParser.
     * In List <String> newArguments: argument(0) - is command.
     */
    public void receiveArguments(List<String> newArguments) {
        arguments = newArguments;
    }

    /**
     * Method execute() - executes command. In this case - command - price, which outputs data of all products in cart
     * and storage, total price of products in cart and total sum of discount on all products in cart to console.
     * parameters - instance of class Cart.
     */
    public void execute(Cart cart) {
        cart.price();
    }
}
