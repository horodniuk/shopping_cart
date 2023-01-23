package commands;

import cart.Cart;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FinishCommand extends Command {

    //Example: finish

    private List<String> arguments = new ArrayList<>();

    public void receiveArguments(List<String> newArguments) {
    }

    public void execute(Cart cart) {
        cart.finish();
    }
}
