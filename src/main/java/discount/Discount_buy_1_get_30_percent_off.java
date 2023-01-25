package discount;

import cart.Product;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

public class Discount_buy_1_get_30_percent_off implements Discount {
    private static final int DISCOUNT_PERCENT = 30;

    @Getter
    private final String discountName = "buy_1_get_30_percentage";

    /**
     * BUY_1_GET_30_PERCENT_OFF - type of discount in which customer gets 30% discount on each product.
     * formula -> discount = product quantity * product price * 0.30
     */
    @Override
    public BigDecimal getDiscount(Product product, Map<Product, Integer> cart) {
        BigDecimal pricePerOneProduct = product.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(cart.get(product));
        BigDecimal percent = new BigDecimal(DISCOUNT_PERCENT).movePointLeft(2);
        return pricePerOneProduct.multiply(quantity.multiply(percent));
    }

    @Override
    public String toString() {
        return  discountName.toUpperCase(Locale.ROOT);
    }
}
