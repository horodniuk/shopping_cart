package commands;

import cart.CartCommandParser;
import lombok.Getter;

import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandAdd extends Command {

    private final Pattern regex = (Pattern.compile("^(add) (" +
            createRegExValues(CartCommandParser.getProducts()) + ") ([0-9]+)"));
}
