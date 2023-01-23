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
    // -> structure add [discount name][product name]
    //pattern of regular expression we divide in three groups (group(1))(group(2))(group(3))
    //     * (group(1)) - command name: discount
    //     * (group(2)) - name of discount
    //     * (group(3)) - name of product
    private final Pattern regex = (Pattern.compile("^(discount) (" +
            createRegExValues(CartCommandParser.getDiscounts()) + ") ("
            + createRegExValues(CartCommandParser.getProducts()) + ")"));

    //method which finds out if Pattern matches string
    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.applyDiscount(CartCommandParser.parseDiscount(arguments.get(1)), arguments.get(2));
    }
}
