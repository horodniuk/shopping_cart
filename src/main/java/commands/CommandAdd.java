package commands;

import cart.CartCommandParser;
import lombok.Getter;

import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandAdd extends Command {

    // Example: add bear 5, add cola 1, add soap 2 -> structure add [product name][product quantity]
    //pattern of regular expression we divide in three groups (group(1))(group(2))(group(3))
    //     * (group(1)) - command name: add
    //     * (group(2)) - it is a product name
    //     * (group(3)) - it is product quantity
    private final Pattern regex = (Pattern.compile("^(add) (" +
            createRegExValues(CartCommandParser.getProducts()) + ") ([0-9]+)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
