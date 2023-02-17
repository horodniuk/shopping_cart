package discount;

import cart.Product;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ToString(onlyExplicitlyIncluded = true)
public class DiscountStorage {
    @ToString.Include
    private Map<Product, Discount> discountMap;    // map of discount types, which are applied on products in Cart
    @Getter
    private BigDecimal discountValue = new BigDecimal(00.00).setScale(2); // total amount of discount on products in
    // cart

    public DiscountStorage() {
        this.discountMap = new HashMap<>();
    }

    /**
     * Method description
     * parameters - instance of class Product, bigDecimal value of discount;
     * Method should remove discount from discount map and subtract from total value of discount value of discount on
     * this product;
     */
    public void removeDiscountValueAndType(Product product, BigDecimal tempDiscountValue) {
        discountMap.remove(product);
        discountValue = discountValue.subtract(tempDiscountValue);
    }

    /**
     * Method description
     * parameters - instance of class Product, bigDecimal value of discount, type of Discount;
     * Method should update discount value and type;
     * first we subtract from total value of discount value of difference (between old discount and new Discount) on
     * this product;
     * next we put in discountMap this product and new discount type.
     */
    public void updateDiscountValueAndType(Product product, BigDecimal difference, Discount newDiscountType) {
        discountValue = discountValue.subtract(difference).setScale(2);
        discountMap.put(product, newDiscountType);
    }

    /**
     * Method description
     * parameters - instance of class Product, bigDecimal value of discount, type of Discount;
     * Method should add new discount in discountMap and add value of new discount to total value of discount;
     * first we add value of new discount to total value of discount;
     * next we put in discountMap this product and new discount type.
     */
    public void addDiscountValueAndType(Product product, BigDecimal newDiscountProductValue, Discount newDiscountType) {
        discountValue = discountValue.add(newDiscountProductValue).setScale(2);
        discountMap.put(product, newDiscountType);
    }

    /**
     * Method description
     * parameters - instance of class Product;
     * Method should return boolean: true if such product exists in discountMap as key (if discount is applied on this
     * product) and false if such product doesn't exist in discountMap.
     */
    public Boolean isDiscountAppliedOnProduct(Product product) {
        return discountMap.containsKey(product);
    }

    /**
     * Method description
     * parameters - instance of class Product;
     * Method should return discount type from discountMap;
     */
    public Discount getDiscountTypeFromMap(Product product) {
        return discountMap.get(product);
    }
}
