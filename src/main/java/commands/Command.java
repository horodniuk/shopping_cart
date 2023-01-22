package commands;

import cart.Cart;
import runner.TextModeExecute;

import java.util.List;
import java.util.regex.Pattern;

public abstract class Command {
    TextModeExecute textModeExecute;
    private Pattern regex;

    public Command() {
        this.textModeExecute = new TextModeExecute();
    }

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public Pattern getRegex() {
        return regex;
    }

    public void execute(Cart cart, List<String> arguments) {
    }
}
