package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

@Getter
public class RemoveCommand extends Command {

    // Example: add bear 5, add cola 1, add soap 2
    private final Pattern regex = (Pattern.compile("^(remove) (" +
            createRegExValues(CartCommandParser.getProducts()) + ") ([0-9]+)"));

    private String productName;
    private int quantity;

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    public void receiveArguments(List<String> newArguments) {
        productName = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.remove(productName, quantity);
    }
}
