package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

@Getter
public class DiscountCommand extends Command {

    //Example: discount buy_1_get_30_percentage cola,  discount buy_3_get_1_free bear
    private final Pattern regex = (Pattern.compile("^(discount) (" +
            createRegExValues(CartCommandParser.getDiscounts()) + ") ("
            + createRegExValues(CartCommandParser.getProducts()) + ")"));
    private String discountName;
    private String productName;

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void receiveArguments(List<String> newArguments) {
        discountName = newArguments.get(1);
        productName = newArguments.get(2);
    }

    public String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    public void execute(Cart cart) {
        cart.applyDiscount(CartCommandParser.parseDiscount(discountName), productName);
    }
}
