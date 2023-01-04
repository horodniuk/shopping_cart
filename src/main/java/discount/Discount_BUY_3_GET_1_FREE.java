package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.Map;

public class Discount_BUY_3_GET_1_FREE implements Discount {
    private static final int NUMBER_ITERATION_FOR_DISCOUNT = 4;

    /**
     * BUY_3_GET_ONE_FREE - тип скидки, при которой покупатель получает каждый четвертый одинаковый товар бесплатно
     * по формуле  ->  скидка = кол-во продукта/4 * цена
     */
    @Override
    public BigDecimal getDiscount(String productName, Map<String, Product> cart) {
        int discountCount = cart.get(productName).getQuantity() / NUMBER_ITERATION_FOR_DISCOUNT;
        if (discountCount == 0) {
            System.out.println("В корзине нет 4 продуктов -" + productName + ", чтобы получить бесплатно четвертый");
            return new BigDecimal(0);
        } else {
            return cart.get(productName).getPrice().multiply(BigDecimal.valueOf(discountCount));
        }
    }
}
