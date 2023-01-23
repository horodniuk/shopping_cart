package commands;

import cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommandAdd extends Command {

    // Example: add bear 5, add cola 1, add soap 2
    private List<String> arguments;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.add(arguments.get(1), Integer.parseInt(arguments.get(2)));
    }
}
