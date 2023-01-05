package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.Map;

public class Discount_BUY_3_GET_1_FREE implements Discount {
    private static final int NUMBER_ITERATION_FOR_DISCOUNT = 4;

    /**
     * BUY_3_GET_ONE_FREE - type of discount in which customer gets every fourth identical product for free
     * formula  ->  discount = amount of products/4 * price of product
     */
    @Override
    public BigDecimal getDiscount(String productName, Map<String, Product> cart) {
        int discountCount = cart.get(productName).getQuantity() / NUMBER_ITERATION_FOR_DISCOUNT;
        if (discountCount == 0) {
            System.out.println("Cart doesn't have 4 units of product -" + productName + ", to get fourth for free");
            return new BigDecimal(0);
        } else {
            return cart.get(productName).getPrice().multiply(BigDecimal.valueOf(discountCount));
        }
    }
}
