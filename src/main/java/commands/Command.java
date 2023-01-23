package commands;

import cart.Cart;
import runner.TextModeExecute;

import java.util.List;
import java.util.regex.Pattern;

public abstract class Command {
    private List<String> arguments;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void execute(Cart cart) {
    }
}
