package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DiscountRegister {
    private Map<Product, Discount> discountMap;    // map of discount types, which are applied on products in Cart

    private BigDecimal discountValue = new BigDecimal(00.00).setScale(2); // total amount of discount on products in cart

    public DiscountRegister() {
        this.discountMap = new HashMap<>();
    }

    public void addDiscountType(Product product, Discount discount) {
        discountMap.put(product, discount);
    }

    public void removeDiscount(Product product) {
        discountMap.remove(product);
    }

    public void subtractDiscountValue(Discount tempDiscount, Product product, Map<Product, Integer> cartMap) {
        discountValue = discountValue.subtract(tempDiscount.getDiscount(product, cartMap));
    }

    public void updateDiscountValue(Discount tempDiscount, BigDecimal oldDiscountProductValue, Product product,
                                    Map<Product, Integer> cartMap) {
        discountValue = discountValue.subtract(oldDiscountProductValue).add(tempDiscount.getDiscount(product, cartMap)).
                setScale(2);
    }

    public void updateDiscount(Product product, BigDecimal newDiscountProductValue, BigDecimal oldDiscountProductValue,
                               Discount newDiscountType) {
        discountValue = discountValue.subtract(oldDiscountProductValue).add(newDiscountProductValue).setScale(2);
        addDiscountType(product, newDiscountType);
    }

    public void addDiscountValue(Product product, BigDecimal newDiscountProductValue, Discount newDiscountType) {
        discountValue = discountValue.add(newDiscountProductValue).setScale(2);
        addDiscountType(product, newDiscountType);
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
        return "DiscountRegister{" +
                "discountMap=" + discountMap +
                ", discountValue=" + discountValue +
                '}';
    }
}
