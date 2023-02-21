package cart;

import discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
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
        Discount discountName = ConsoleCommandParser.parseDiscount(arguments.get(1));
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
}
