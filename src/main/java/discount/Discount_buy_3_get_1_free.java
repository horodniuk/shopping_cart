package discount;

import cart.Product;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;


public class Discount_buy_3_get_1_free implements Discount {
    private static final int NUMBER_ITERATION_FOR_DISCOUNT = 4;
    @Getter
    private final String discountName = "buy_3_get_1_free";

    /**
     * BUY_3_GET_ONE_FREE - type of discount in which customer gets every fourth identical product for free
     * formula  ->  discount = amount of products/4 * price of product
     */
    @Override
    public BigDecimal getDiscount(Product product, Map<Product, Integer> cart) {
        int discountCount = cart.get(product) / NUMBER_ITERATION_FOR_DISCOUNT;
        if (discountCount == 0) {
            System.out.println("Cart doesn't have 4 units of product -" + product.getName() + ", to get fourth for free");
            return new BigDecimal(0);
        } else {
            return product.getPrice().multiply(BigDecimal.valueOf(discountCount));
        }
    }

    @Override
    public String toString() {
        return  discountName.toUpperCase(Locale.ROOT);
    }

}
