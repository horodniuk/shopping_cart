package runner;

import cart.Cart;
import cart.ConsoleCommandParser;
import commands.*;

import java.util.Optional;

public class TextCommandExecutor {

    /**
     * Method description
     * Method parameters - string line which we get after reading file or getting it from console, instance of
     * class Cart;
     * This methode is made to execute commands which we get after parsing the string of command.
     * First we create instance of class ConsoleCommandParser;
     * next we get Optional of class Command with the help of method parse()
     * Then checking if Optional of Command is empty or not.
     * If it's empty then we print message to console that user entered unknown command,
     * because we couldn't parse it with method parse()
     * IF Optional of Command is not empty: then we get command from instance of Optional ParsedCommand
     * and assign to a variable command;
     * after that we start method execute() which executes specified command.
     * method parse() - parses the received line (from file or from console);
     * method execute() - executes specified command.
     */
    public void executeCommand(String line, Cart cart) {
        ConsoleCommandParser consoleCommandParser = new ConsoleCommandParser(cart);
        Optional<Command> parsedCommandOptional = consoleCommandParser.parse(line);
        if (parsedCommandOptional.isEmpty())
            System.out.println("Unknown command, try again, for example \"add beer 5\"");
        else {
            Command command = parsedCommandOptional.get();
            command.execute(cart);
        }
    }
}

