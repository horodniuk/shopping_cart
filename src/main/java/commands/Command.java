package commands;

import cart.Cart;

import java.util.List;

public abstract class Command {
    private List<String> arguments;

    /**
     * Method receiveArguments() - receives arguments (strings) in method parse() in class ConsoleCommandParser.
     * parameters - List of strings
     */
    public abstract void receiveArguments(List<String> newArguments);

    /**
     * Method execute() - executes command.
     * parameters - instance of class Cart.
     */
    public abstract void execute(Cart cart);
}
