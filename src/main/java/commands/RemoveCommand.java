package commands;

import cart.Cart;

import java.util.List;

public class RemoveCommand extends Command {

    private String productName;
    private int quantity;

    public void receiveArguments(List<String> newArguments) {
        productName = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }

    public void execute(Cart cart) {
        cart.remove(productName, quantity);
    }
}
