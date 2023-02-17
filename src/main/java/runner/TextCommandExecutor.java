package runner;

import cart.Cart;
import cart.CommandService;
import cart.Commands;
import cart.ConsoleCommandParser;

public class TextCommandExecutor {

    /**
     * Method description
     * Method parameters - string line which we get after reading file or getting it from console, instance of
     * class Cart;
     * This methode is made to execute commands which we get after parsing the string of command.
     * First we create instance of class ConsoleCommandParser;
     * next we get Command with the help of method parse()
     * after that we start method execute() which executes specified command.
     * method parse() - parses the received line (from file or from console);
     * method execute() - executes specified command.
     */
    public void executeCommand(String line, Cart cart) {
        ConsoleCommandParser consoleCommandParser = new ConsoleCommandParser(cart);
        CommandService commandService = consoleCommandParser.parse(line);
        Commands command = commandService.getCommand();
        switch (command) {
            case ADD -> commandService.addProductCommand(cart);
            case REMOVE -> commandService.removeProductCommand(cart);
            case DISCOUNT -> commandService.applyDiscountCommand(cart);
            case PRICE -> commandService.priceCommand(cart);
            case FINISH -> commandService.finishCommand(cart);
        }
    }
}

