package commands;

import cart.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class Command {
    private Pattern regex;

    private List<String> arguments = new ArrayList<>();

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public Pattern getRegex() {
        return regex;
    }

    public void receiveArguments(List<String> arguments){
    }

    public String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    public void execute(Cart cart) {
    }
}
