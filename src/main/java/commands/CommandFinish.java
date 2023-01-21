package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

@Getter
public class CommandFinish extends Command {

    //Example: finish
    private final Pattern regex = (Pattern.compile("(^finish$)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.finish();
    }
}
