package commands;

import cart.Cart;
import cart.ConsoleCommandParser;
import discount.Discount;
import lombok.Getter;

import java.util.List;

@Getter
public class DiscountCommand extends Command {

    //Example: discount buy_1_get_30_percentage cola,  discount buy_3_get_1_free bear

    private Discount discountName;
    private String productName;

    public void receiveArguments(List<String> newArguments) {
        discountName = ConsoleCommandParser.parseDiscount(newArguments.get(1));
        productName = newArguments.get(2);
    }

    public void execute(Cart cart) {
        cart.applyDiscount(discountName, productName);
    }
}
