package commands;

import cart.Cart;
import cart.Product;

import java.util.List;

public class AddCommand extends Command {

    private String product;
    private int quantity;

    public void receiveArguments(List<String> newArguments) {
        product = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }

    public void execute(Cart cart) {
        cart.add(product, quantity);
    }

}
