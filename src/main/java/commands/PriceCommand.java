package commands;

import cart.Cart;

import java.util.List;

public class PriceCommand extends Command {

    private List<String> arguments;

    public void receiveArguments(List<String> newArguments) {
        arguments = newArguments;
    }

    public void execute(Cart cart) {
        cart.price();
    }
}
