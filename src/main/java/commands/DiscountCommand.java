package commands;

import cart.Cart;
import cart.ConsoleCommandParser;
import discount.Discount;

import java.util.List;

public class DiscountCommand extends Command {

    private Discount discountName; // name of discount
    private String productName; // name of product

    /**
     * Method receiveArguments() - receives arguments (strings) in method parse() in class ConsoleCommandParser.
     * In List <String> newArguments: argument(0) - is command, argument (1) - discountName, argument (2) - productName.
     */
    public void receiveArguments(List<String> newArguments) {
        discountName = ConsoleCommandParser.parseDiscount(newArguments.get(1));
        productName = newArguments.get(2);
    }
    /**
     * Method execute() - executes command. In this case - command - applyDiscount, which applies discount on selected
     * product in cart and adds this product and discount in discountMap in Cart.
     * parameters - instance of class Cart.
     */
    public void execute(Cart cart) {
        cart.applyDiscount(discountName, productName);
    }
}
