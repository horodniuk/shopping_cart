package commands;

import cart.Cart;

import java.util.List;

public abstract class Command {
    private List<String> arguments;

    public abstract void receiveArguments(List<String> newArguments);

    public abstract void execute(Cart cart) ;
}
