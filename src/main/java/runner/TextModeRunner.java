package runner;

import cart.Cart;
import cart.CartCommandParser;
import cart.ParsedCommand;
import commands.*;

import java.util.List;
import java.util.Optional;

public class TextModeRunner {

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
    public void executeCommand(String line, Cart cart) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        Optional<ParsedCommand> parsedCommandOptional = cartCommandParser.parse(line);
        if (parsedCommandOptional.isEmpty())
            System.out.println("Unknown command, try again, for example \"add beer 5\"");
        else {
            Command command = parsedCommandOptional.get().getCommand();
            List<String> arguments = parsedCommandOptional.get().getArguments();
            switch (command.getClass().getSimpleName()) {
                case "CommandDiscount" -> {
                    cart.applyDiscount(cartCommandParser.parseDiscount(arguments.get(1)), arguments.get(2));
                }
                case "CommandAdd" -> {
                    cart.add(arguments.get(1), Integer.parseInt(arguments.get(2)));
                }
                case "CommandPrice" -> cart.price();
                case "CommandFinish" -> System.out.println("Done");
            }
        }
    }
}

