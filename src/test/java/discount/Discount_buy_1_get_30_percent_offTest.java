package discount;

import cart.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class Discount_buy_1_get_30_percent_offTest {
    Discount_buy_1_get_30_percent_off discount;
    Product product = new Product(1, "apple", new BigDecimal(20));

    @BeforeEach
    void init() {
        discount = new Discount_buy_1_get_30_percent_off();
    }

    private static Stream<Arguments> checkThirtyPercentDiscount() {
        return Stream.of(                                   // priceProduct * quantity * 0.3
                arguments(1,   new BigDecimal(6.0)),     //  20  * 1 * 0,3 = 6
                arguments(0,   new BigDecimal(0.0)),     //  20  * 0 * 0,3 = 0
                arguments(3,   new BigDecimal(18.0)),     //  20  * 3 * 0,3 = 18
                arguments(4,   new BigDecimal(24.0)),    //  20  * 4 * 0,3 = 24
                arguments(10,  new BigDecimal(60.0)),   //  20  * 10 * 0,3 = 60
                arguments(15,  new BigDecimal(90.0)),   //  20  * 15 * 0,3 = 90
                arguments(20,  new BigDecimal(120.0))   //  20  * 20 * 0,3 = 120
        );
    }

    // testing the discount formula with different quantities of product.
    // The cost of the product is fixed at 20.
    @ParameterizedTest
    @MethodSource("checkThirtyPercentDiscount")
    void thirtyPercentDiscountWhenProductPriceTwenty(int quantity, BigDecimal resultOperation) {
        double expected = resultOperation.doubleValue();
        double actual = discount.getDiscount(product, quantity).doubleValue();
        assertEquals(expected, actual, 0.01);
    }

}