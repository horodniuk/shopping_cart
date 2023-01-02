package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.Map;

public class Discount_BUY_1_GET_30_PERCENT_OFF implements Discount {
    private static int discountPercent = 30;

    /**
     * BUY_1_GET_30_PERCENT_OFF - тип скидки, при которой покупатель получает 30% скидки на каждый товар:
     * формула -> скидка = кол-во продукта * цену продукта * 0.30
     */
    @Override
    public BigDecimal getDiscount(String productName, Map<String, Product> cart) {
       BigDecimal pricePerOneProduct = cart.get(productName).getPrice();
       BigDecimal quantiti = BigDecimal.valueOf(cart.get(productName).getQuantity());
       BigDecimal percent = new BigDecimal(discountPercent).movePointLeft(2);
      //  System.out.println(pricePerOneProduct + " * " + quantiti + " * " + percent);
        return pricePerOneProduct.multiply(quantiti.multiply(percent));
    }
}
