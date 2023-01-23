package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandRemoveSomeName extends Command {
    // Example: remove bear, remove cola, remove soap
    private final Pattern regex = Pattern.compile("^(remove) (" +
            createRegExValues(CartCommandParser.getProducts()) + ")");

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.removeProductSameName(arguments.get(1));
    }
}
