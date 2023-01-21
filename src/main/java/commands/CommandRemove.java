package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandRemove extends Command {

    // Example: add bear 5, add cola 1, add soap 2
    private final Pattern regex = (Pattern.compile("^(remove) (" +
            createRegExValues(CartCommandParser.getProducts()) + ") ([0-9]+)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void execute(Cart cart, String productName, String quantity) {
        cart.remove(productName, Integer.parseInt(quantity));
    }
}
