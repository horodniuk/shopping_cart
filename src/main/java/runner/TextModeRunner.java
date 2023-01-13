package runner;

import cart.Cart;
import cart.CartCommandParser;

import java.util.List;
import java.util.Optional;

public class TextModeRunner {

    public void executeCommand(String line, Cart cart) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        Optional<ParsedCommand> parsedCommandOptional = cartCommandParser.parse(line);
        if (parsedCommandOptional.isEmpty())
            System.out.println("Unknown command, try again, for example \"add beer 5\"");
        else {
            Command command = parsedCommandOptional.get().getCommand();
            List<String> arguments = parsedCommandOptional.get().getArguments();
            switch (command) {
                case DISCOUNT -> {
                    cart.applyDiscount(cartCommandParser.parseDiscount(arguments.get(1)), arguments.get(2));
                }
                case ADD -> {
                    cart.add(arguments.get(1), Integer.parseInt(arguments.get(2)));
                }
                case PRICE -> cart.price();
                case FINISH -> System.out.println("Done");
            }
        }
    }
}

