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

    public void addDiscount(Product product, Discount discount) {
        discountMap.put(product, discount);
    }

    public void removeDiscount(Product product) {
        discountMap.remove(product);
    }

    public void subtractDiscountValue(Discount tempDiscount, Product product, Map<Product, Integer> cartMap) {
        discountValue = discountValue.subtract(tempDiscount.getDiscount(product, cartMap));
    }

    public void updateDiscountValue(Discount tempDiscount, BigDecimal discountProductValue, Product product,
                                    Map<Product, Integer> cartMap) {
        discountValue = discountValue.subtract(discountProductValue).add(tempDiscount.getDiscount(product, cartMap));
    }

    public BigDecimal updateDiscount(Product product, BigDecimal discountProductValue, Map<Product, Integer> cartMap) {
        if (discountMap.containsKey(product)) {
            BigDecimal oldDiscountValueProduct = discountMap.get(product).getDiscount(product, cartMap);
            discountValue = discountValue.subtract(oldDiscountValueProduct);
        }
        return discountValue.add(discountProductValue).setScale(2);
    }

    public Boolean isDiscountAppliedOnProduct(Product product) {
        return discountMap.containsKey(product);
    }

    public Discount getDiscountFromMap(Product product) {
        return discountMap.get(product);
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    @Override
    public String toString() {
        return "DiscountRegister{" +
                "discountMap=" + discountMap +
                ", discount=" + discountValue +
                '}';
    }
}
