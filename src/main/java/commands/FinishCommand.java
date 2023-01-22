package commands;

import cart.Cart;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
public class FinishCommand extends Command {

    //Example: finish
    private final Pattern regex = (Pattern.compile("(^finish$)"));

    private List<String> arguments = new ArrayList<>();

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void receiveArguments(List<String> newArguments) {
    }

    public void execute(Cart cart) {
        cart.finish();
    }
}
