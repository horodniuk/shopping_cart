package commands;

import cart.Cart;
import lombok.Getter;

import java.util.List;

@Getter
public class RemoveCommand extends Command {

    // Example: add bear 5, add cola 1, add soap 2

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
