package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommandDiscount extends Command {

    //Example: discount buy_1_get_30_percentage cola,  discount buy_3_get_1_free bear
    private List<String> arguments;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.applyDiscount(CartCommandParser.parseDiscount(arguments.get(1)), arguments.get(2));
    }
}
