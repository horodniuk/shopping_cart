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
     * Method description
     * parameters - product name, int quantity of this product;
     * getDiscount() should return sum of discount which equals 30% from product price on selected product.
     * formula -> discount = product quantity * product price * 0.30
     */
    @Override
    public BigDecimal getDiscount(Product product, int quantityFromCart) {
        BigDecimal pricePerOneProduct = product.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(quantityFromCart);
        BigDecimal percent = new BigDecimal(DISCOUNT_PERCENT).movePointLeft(2);
        return pricePerOneProduct.multiply(quantity.multiply(percent));
    }

    @Override
    public String toString() {
        return  discountName.toUpperCase(Locale.ROOT);
    }
}
