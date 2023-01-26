package commands;

import cart.Cart;

import java.util.List;

public class FinishCommand extends Command {

    private List<String> arguments; // list of arguments that may be needed in future
    /**
     * Method receiveArguments() - receives arguments (strings) in method parse() in class ConsoleCommandParser.
     * In List <String> newArguments: argument(0) - is command.
     */
    public void receiveArguments(List<String> newArguments) {
        arguments = newArguments;
    }
    /**
     * Method execute() - executes command. In this case - command - finish, which finishes the work of the program and
     * writes changes (if they were) in storage.
     * parameters - instance of class Cart.
     */
    public void execute(Cart cart) {
        cart.finish();
    }
}
