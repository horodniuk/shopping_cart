package cart;

import discount.Discount;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartCommandParser {
    private Cart cart;
    private List<String> discounds;
    private List<String> products;

    public CartCommandParser(Cart cart) {
        this.cart = cart;
        this.products = cart.getStorageMap().keySet().stream().toList(); // получаем все название товаров в хранилище
        this.discounds = List.of("buy_1_get_30_percentage", "buy_3_get_1_free"); // название скидок;
    }
    public boolean parse(String line) {
        // Example: add bear 5, add cola 1, add soap 2
        String productRegEx = "^(add) (" + createRegExValues(products) +") ([0-9]+)";

        //Example: discount buy_1_get_30_percentage cola,  discount buy_3_get_1_free bear
        String discountRegEx = "^(discount) (" + createRegExValues(discounds) + ") (" + createRegExValues(products) + ")";

        Matcher productCommandPatternMatcher = Pattern.compile(productRegEx).matcher(line);
        Matcher discountCommandPatternMatcher = Pattern.compile(discountRegEx).matcher(line);

        if (productCommandPatternMatcher.find()) {
            String productName = productCommandPatternMatcher.group(2);
            int countProduct = Integer.parseInt(productCommandPatternMatcher.group(3));
            cart.add(productName, countProduct);
            return true;
        }
        if (discountCommandPatternMatcher.find()){
            String productName = discountCommandPatternMatcher.group(3);
            Discount currentDiscount = parseDiscount(discountCommandPatternMatcher.group(2));
            cart.applyDiscount(currentDiscount, productName);
            return true;
        } else {
            return false;
        }
    }

    /*
     * в зависимости от названия скидки  создать объект скидки
     * buy_1_get_30_percentage -> Discount_BUY_1_GET_30_PERCENT_OFF();
     * buy_3_get_1_free -> Discount_BUY_3_GET_1_FREE();
     */
    private Discount parseDiscount(String nameComand) {
        if (nameComand.equals("buy_1_get_30_percentage")){
            return new Discount_BUY_1_GET_30_PERCENT_OFF();
        } else {
            return new Discount_BUY_3_GET_1_FREE();
        }
    }

    /*
     * из листа конвертируем в сроку, что бы применить в регулярном выражении
     * Например List.of("buy_1_get_30_percentage", "buy_3_get_1_free") -> "buy_1_get_30_percentage|buy_3_get_1_free"
     */
    private String createRegExValues(List<String> values) {
        return String.join("|", values);
    }
}
