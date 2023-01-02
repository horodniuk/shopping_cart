package discount;

import cart.Product;

import java.math.BigDecimal;
import java.util.Map;

/**
 * через этот интерфейс будем подсчитывать скидку по разным формулам
 */
public interface Discount {
       /**
        * метод getDiscount() должен возвращать сумму скидки в зависимости от реализации
        * парамеры:
        * product name - название продукта;
        * cart - корзина из товарами;
        */
       BigDecimal getDiscount(String productName, Map<String, Product> cart);
}
