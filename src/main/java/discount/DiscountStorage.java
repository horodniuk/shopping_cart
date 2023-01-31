package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DiscountStorage {
    private Map<Product, Discount> discountMap;    // map of discount types, which are applied on products in Cart

    private BigDecimal discountValue = new BigDecimal(00.00).setScale(2); // total amount of discount on products in
    // cart

    public DiscountStorage() {
        this.discountMap = new HashMap<>();
    }

    public void removeDiscountValueAndType(Product product, BigDecimal tempDiscountValue) {
        discountMap.remove(product);
        discountValue = discountValue.subtract(tempDiscountValue);
    }

    public void updateDiscountValueAndType(Product product, BigDecimal difference, Discount newDiscountType) {
        discountValue = discountValue.subtract(difference).setScale(2);
        discountMap.put(product, newDiscountType);
    }

    public void addDiscountValueAndType(Product product, BigDecimal newDiscountProductValue, Discount newDiscountType) {
        discountValue = discountValue.add(newDiscountProductValue).setScale(2);
        discountMap.put(product, newDiscountType);
    }

    public Boolean isDiscountAppliedOnProduct(Product product) {
        return discountMap.containsKey(product);
    }

    public Discount getDiscountTypeFromMap(Product product) {
        return discountMap.get(product);
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    @Override
    public String toString() {
        return "DiscountStorage{" +
                "discountMap=" + discountMap +
                ", discountValue=" + discountValue +
                '}';
    }
}
