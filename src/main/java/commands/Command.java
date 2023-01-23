package commands;

import cart.Cart;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    private List<String> arguments = new ArrayList<>();


    public void receiveArguments(List<String> arguments) {
    }

    public void execute(Cart cart) {
    }
}
