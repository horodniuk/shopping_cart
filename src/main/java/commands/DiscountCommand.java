package commands;

import cart.Cart;
import cart.ConsoleCommandParser;
import discount.Discount;

import java.util.List;

public class DiscountCommand extends Command {

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
