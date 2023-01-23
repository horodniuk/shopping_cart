package commands;

import cart.Cart;

import java.util.List;

public abstract class Command {
    private List<String> arguments;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void execute(Cart cart, List<String> arguments) {
    }
}
