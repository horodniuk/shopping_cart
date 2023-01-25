package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.Map;

/**
 * interface for calculation of discounts with different formulas
 */
public interface Discount {
    /**
     * Method getDiscount() should return sum of discount depending on realisation
     * parameters:
     * product name;
     * cart - cart with products;
     */
    BigDecimal getDiscount(Product product, Map<Product, Integer> cart);

    String getDiscountName();
}


