package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

@Getter
public class AddCommand extends Command {

    // Example: add bear 5, add cola 1, add soap 2
    private final Pattern regex = (Pattern.compile("^(add) (" +
            createRegExValues(CartCommandParser.getProducts()) + ") ([0-9]+)"));

    private String productName;
    private int quantity;

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void receiveArguments(List<String> newArguments){
        productName = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }

    public void execute(Cart cart) {
        cart.add(productName, quantity);
    }
    /*
     * from list converting to String, to apply in regular expression
     * For example: List.of("buy_1_get_30_percentage", "buy_3_get_1_free") ->
     * "buy_1_get_30_percentage|buy_3_get_1_free"
     */
    public String createRegExValues(List<String> values) {
        return String.join("|", values);
    }
}
