package runner;

import cart.Cart;
import cart.CartCommandParser;

import commands.*;

import java.util.List;
import java.util.Optional;

public class TextModeExecute {

    /**
     * Method description
     * Method parameters - string line which we get after reading file or getting it from console
     * This methode is made to execute commands which we get after parsing the string of command.
     * First we get Optional of Class ParsedCommand with the help of method cartCommandParser.parse()
     * Then checking if Optional of ParsedCommand is empty or not.
     * If it's empty then we print message to console that user entered unknown command,
     * because we couldn't parse it with method parse()
     * IF Optional of ParsedCommand is not empty: then we get command from instance of Oprional ParsedCommand
     * and assign to a variable command;
     * also we are getting string arguments from parsedCommandOptional and assign them to new List arguments.
     * after that we are checking in cycle "switch case" if simple name of class of our command fits any of command
     * case CommandDiscount -> we apply discount on chosen product, where
     * cartCommandParser.parseDiscount(arguments.get(1)) is type of discount and arguments.get(2) - name of the product;
     * case CommandAdd -> we are adding product to cart, where argument.get(1) is product name and
     * Integer.parseInt(arguments.get(2)) is product quantity;
     * case CommandPrice -> we output data to console: total price of the products, total discount and
     * amount of products in cart and in storage;
     * case CommandFinish -> we finish the work of the program.
     */
    public void executeCommand(String line, Cart cart) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        Optional<Command> parsedCommandOptional = cartCommandParser.parse(line);
        if (parsedCommandOptional.isEmpty())
            System.out.println("Unknown command, try again, for example \"add beer 5\"");
        else {
            Command command = parsedCommandOptional.get();
            List<String> arguments = parsedCommandOptional.get().getArguments();
            command.execute(cart, arguments);
        }
    }
}

