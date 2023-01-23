package commands;

import cart.Cart;
import lombok.Getter;

import java.util.List;

@Getter
public class PriceCommand extends Command {

    //Example: price
    private List<String> arguments;

    public void receiveArguments(List<String> newArguments) {
        arguments = newArguments;
    }

    public void execute(Cart cart) {
        cart.price();
    }
}
