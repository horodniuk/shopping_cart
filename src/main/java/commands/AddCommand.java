package commands;

import cart.Cart;
import lombok.Getter;

import java.util.List;


@Getter
public class AddCommand extends Command {

    private String productName;
    private int quantity;

    public void receiveArguments(List<String> newArguments) {
        productName = newArguments.get(1);
        quantity = Integer.parseInt(newArguments.get(2));
    }

    public void execute(Cart cart) {
        cart.add(productName, quantity);
    }

}