package discount;

import cart.Product;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;
@ToString
public class Discount_buy_1_get_30_percent_off implements Discount {
    private static final int DISCOUNT_PERCENT = 30;

    /**
     * BUY_1_GET_30_PERCENT_OFF - type of discount in which customer gets 30% discount on each product.
     * formula -> discount = product quantity * product price * 0.30
     */
    @Override
    public BigDecimal getDiscount(String productName, Map<String, Product> cart) {
        BigDecimal pricePerOneProduct = cart.get(productName).getPrice();
        BigDecimal quantity = BigDecimal.valueOf(cart.get(productName).getQuantity());
        BigDecimal percent = new BigDecimal(DISCOUNT_PERCENT).movePointLeft(2);
        return pricePerOneProduct.multiply(quantity.multiply(percent));
    }
}
