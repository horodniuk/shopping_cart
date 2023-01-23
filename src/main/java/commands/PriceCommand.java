package commands;

import cart.Cart;
import lombok.Getter;

import java.util.List;

@Getter
public class PriceCommand extends Command {

    //Example: price

    public void receiveArguments(List<String> newArguments) {
    }

    public void execute(Cart cart) {
        cart.price();
    }
}
