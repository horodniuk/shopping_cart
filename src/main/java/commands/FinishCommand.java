package commands;

import cart.Cart;

import java.util.List;

public class FinishCommand extends Command {

    private List<String> arguments;

    public void receiveArguments(List<String> newArguments) {
        arguments = newArguments;
    }

    public void execute(Cart cart) {
        cart.finish();
    }
}
