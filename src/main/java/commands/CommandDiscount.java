package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandDiscount extends Command {

    //Example: discount buy_1_get_30_percentage cola,  discount buy_3_get_1_free bear
    private final Pattern regex = (Pattern.compile("^(discount) (" +
            createRegExValues(CartCommandParser.getDiscounts()) + ") ("
            + createRegExValues(CartCommandParser.getProducts()) + ")"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.applyDiscount(CartCommandParser.parseDiscount(arguments.get(1)), arguments.get(2));
    }
}
