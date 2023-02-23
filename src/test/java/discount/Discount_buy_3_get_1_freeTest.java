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

class Discount_buy_3_get_1_freeTest {
    Discount_buy_3_get_1_free discount;
    Product product = new Product(1, "apple", new BigDecimal(20));

    @BeforeEach
    void init() {
        discount = new Discount_buy_3_get_1_free();
    }

    private static Stream<Arguments> checkEveryFourthProductIsFree() {
        return Stream.of(                                   // priceProduct * quantity * 0.3
                arguments(1,  new BigDecimal(0.0)),     //  count 1 less 4 = 0
                arguments(3,  new BigDecimal(0.0)),     //  count 3 less 4 = 0
                arguments(4, new BigDecimal(20.0)),     //  4  / 4   = 1 * 20 = 20
                arguments(7,  new BigDecimal(20.0)),    //  7  / 4   = 1 * 20 = 20
                arguments(8,  new BigDecimal(40.0)),    //  8  / 4   = 2 * 20 = 40
                arguments(15,  new BigDecimal(60.0)),   //  15  / 4  = 3 * 20 = 60
                arguments(20,  new BigDecimal(100.0))   //  20 / 4  = 5 * 20 = 100
        );
    }
    // testing the discount formula with different quantities of product.
    // The cost of the product is fixed at 20.
    @ParameterizedTest
    @MethodSource("checkEveryFourthProductIsFree")
    void everyFourthProductIsFreeWhenProductPriceTwenty(int quantity, BigDecimal resultOperation) {
        double expected = resultOperation.doubleValue();
        double actual = discount.getDiscount(product, quantity).doubleValue();
        assertEquals(expected, actual, 0.01);
    }


}