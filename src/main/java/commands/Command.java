package commands;

import cart.Cart;
import cart.CartCommandParser;
import runner.TextModeRunner;

import java.util.List;
import java.util.regex.Pattern;

public abstract class Command {
    TextModeRunner textModeRunner;
    private Pattern regex;

    public Command() {
        this.textModeRunner = new TextModeRunner();
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
