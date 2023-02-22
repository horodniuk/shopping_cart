package cart;

import discount.Discount;
import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@Slf4j
public class CommandService {
    private List<String> arguments;
    private Commands command;

    /**
     * Method addProductCommand() - executes add command, which adds product in given quantity in cart Map.
     * parameters - instance of class Cart.
     */
    public void addProductCommand(Cart cart) {
        String product = arguments.get(1);
        int quantity = Integer.parseInt(arguments.get(2));
        cart.add(product, quantity);
    }

    /**
     * Method removeProductCommand() - executes remove command, which removes product in given quantity from cart Map.
     * parameters - instance of class Cart.
     */
    public void removeProductCommand(Cart cart) {
        String product = arguments.get(1);
        int quantity = Integer.parseInt(arguments.get(2));
        cart.remove(product, quantity);
    }

    /**
     * Method applyDiscountCommand() - executes discount command, which applies discount on selected product in cart
     * and adds this product and discount in discountMap in Cart.
     * parameters - instance of class Cart.
     */
    public void applyDiscountCommand(Cart cart) {
        Discount discountName = parseDiscount(arguments.get(1));
        String product = arguments.get(2);
        cart.applyDiscount(discountName, product);
    }

    /**
     * Method finishCommand() - executes finish command, which finishes the work of the program and writes changes
     * (if they were) in storage.
     * parameters - instance of class Cart.
     */
    public void finishCommand(Cart cart) {
        cart.finish();
    }

    /**
     * Method priceCommand() - executes price command, which outputs data of all products in cart and storage,
     * total price of products in cart and total sum of discount on all products in cart to console.
     * parameters - instance of class Cart.
     */
    public void priceCommand(Cart cart) {
        cart.price();
    }

    /**
     * Method description
     * Method parameters - string name of command
     * depending on discount name we create discount object
     * buy_1_get_30_percentage -> Discount_BUY_1_GET_30_PERCENT_OFF();
     * buy_3_get_1_free -> Discount_BUY_3_GET_1_FREE();
     * return - we return instance of discount.
     */
    private Discount parseDiscount(String nameCommand) {
        return Stream.of(new Discount_buy_1_get_30_percent_off(), new Discount_buy_3_get_1_free()).
                filter(discount -> discount.getDiscountName().equals(nameCommand)).findFirst().orElseThrow();
    }
}
