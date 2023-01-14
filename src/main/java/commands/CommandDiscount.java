package commands;

import cart.CartCommandParser;
import lombok.Getter;

import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandDiscount extends Command {

    private final Pattern regex = (Pattern.compile("^(discount) (" +
            createRegExValues(CartCommandParser.getDiscounts()) + ") ("
            + createRegExValues(CartCommandParser.getProducts()) + ")"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
