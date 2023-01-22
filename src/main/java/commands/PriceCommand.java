package commands;

import cart.Cart;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

@Getter
public class PriceCommand extends Command {

    //Example: price
    private final Pattern regex = (Pattern.compile("(^price$)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void receiveArguments(List<String> newArguments) {
    }

    public String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    public void execute(Cart cart) {
        cart.price();
    }
}
