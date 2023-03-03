package cart;

import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CartTest {
    static final String pathTestSoreage = "src/test/resources/test_storage.json";
    static final Storage storage = new StorageWithJson(new File(pathTestSoreage));
    static Cart cartByTestingAddMethod = new Cart(storage);
    static Cart cartByTestingRemoveMethod = new Cart(storage);
    static Cart cartByTestingDiscount30Persent = new Cart(storage);
    static Cart cartByTestringDiscaunt3Buy1Free = new Cart(storage);
    static Cart cartByTestringrRemoveAllPrduct = new Cart(storage);

    static {
        storage.load();
        fillCartTemplate(cartByTestingAddMethod);
        fillCartTemplate(cartByTestingRemoveMethod);
        fillCartTemplate(cartByTestingDiscount30Persent);
        fillCartTemplate(cartByTestringDiscaunt3Buy1Free);
        fillCartTemplate(cartByTestringrRemoveAllPrduct);
    }

    private static void fillCartTemplate(Cart cart) {
        cart.add("beer", 10);
        cart.add("cola", 10);
        cart.add("soap", 10);
    }

    /**
     * cartByTestingAddMethod
     * ("beer", 10);
     * ("cola", 10);
     * ("soap", 10);
     */
    private static Stream<Arguments> addProduct() {
        return Stream.of(
                arguments("beer", 2, 12),     //  beer was 10 add 2 became 12
                arguments("beer", 3, 15),     //  beer was 12 add 3 became 15
                arguments("beer", 5, 20),     //  beer was 15 add 5 became 20
                arguments("cola", 1, 11),     //  cola was 10 add 1 became 11
                arguments("cola", 5, 16),     //  cola was 11 add 5 became 16
                arguments("cola", 4, 20),     //  cola was 14 add 1 became 20
                arguments("soap", 0, 10),     //  soap was 10 add 0 became 10
                arguments("soap", 5, 15),     //  soap was 15 add 5 became 15
                arguments("soap", 5, 20)      //  soap was 15 add 5 became 20
        );
    }

    // Testing product additions from the cart.
    // When adding a product, its quantity should increase
    @ParameterizedTest
    @MethodSource("addProduct")
    @Order(1)
    void checkProductAddInShoppngCart(String productName, int quantity, int resultQuantityPerProduct) {
        int expected = resultQuantityPerProduct;
        cartByTestingAddMethod.add(productName, quantity);
        Product product = cartByTestingAddMethod.getStorage().getProductByName(productName);
        int actual = cartByTestingAddMethod.getCartMap().get(product);
        assertEquals(expected, actual);
    }


    /**
     * cartByTestingRemoveMethod
     * ("beer", 10);
     * ("cola", 10);
     * ("soap", 10);
     */
    private static Stream<Arguments> removeProduct() {
        return Stream.of(
                arguments("beer", 2, 8),     //  beer was 10 remove 2 became 2
                arguments("beer", 3, 5),     //  beer was 2 remove 3 became 5
                arguments("beer", 4, 1),     //  beer was 5 remove 5 became 10
                arguments("cola", 1, 9),     //  cola was 0 remove 1 became 1
                arguments("cola", 5, 4),     //  cola was 1 remove 5 became 6
                arguments("cola", 3, 1),     //  cola was 4 remove 1 became 10
                arguments("soap", 0, 10),     //  soap was 0 remove 0 became 0
                arguments("soap", 5, 5),     //  soap was 5 remove 5 became 5
                arguments("soap", 4, 1)      //  soap was 5 remove 5 became 10
        );
    }

    // Testing the removal of products from the cart.
    // When adding a product, its quantity should increase
    @ParameterizedTest
    @MethodSource("removeProduct")
    @Order(2)
    void checkProductRemoveInShoppngCart(String productName, int quantity, int resultQuantityPerProduct) {
        int expected = resultQuantityPerProduct;
        cartByTestingRemoveMethod.remove(productName, quantity);
        Product product = cartByTestingRemoveMethod.getStorage().getProductByName(productName);
        int actual = cartByTestingRemoveMethod.getCartMap().get(product);
        assertEquals(expected, actual);
    }


    /**
     * cartByTestingDiscount30Persent
     * ("beer",price:50, quantity 10);
     * ("cola", price:20, quantity 10);
     * ("soap", price:30, quantity 10);
     * Price: 1000
     */
    // Testing the apply discount on a product
    // Check the price of the cart, taking into this discount.
    private static Stream<Arguments> applyDiscount30Persent() {
        return Stream.of(
                arguments("beer", new BigDecimal(850.0)),     //  cart was 1000 became 850  beer(10*50*0,3) = 150
                arguments("soap", new BigDecimal(760.0)),     //  cart was 850 became 760  soap(10*30*0,3) = 90
                arguments("cola", new BigDecimal(700.0)),     //  cart was 760 became 700  cola(10*20*0,3) = 60
                arguments("beer", new BigDecimal(700.0)),     //  discount does not apply product twice
                arguments("soap", new BigDecimal(700.0)),     //  discount does not apply product twice
                arguments("cola", new BigDecimal(700.0))     //   discount does not apply product twice
        );
    }

    @ParameterizedTest
    @MethodSource("applyDiscount30Persent")
    @Order(3)
    void checkProductApplyDiscount30PersentInShoppngCart(String productName, BigDecimal priceCartAfterApplyDiscount) {
        cartByTestingDiscount30Persent.applyDiscount(new Discount_buy_1_get_30_percent_off(), productName);
        double expected = priceCartAfterApplyDiscount.doubleValue();
        double actual = cartByTestingDiscount30Persent.getPrice().doubleValue();
        assertEquals(expected, actual, 0.01);
    }


    /**
     * cartByTestingDiscount30Persent
     * ("beer",price:50, quantity 10);
     * ("cola", price:20, quantity 10);
     * ("soap", price:30, quantity 10);
     * Price: 1000
     */
    // Testing the apply discount on a product
    // Check the price of the cart, taking into this discount.
    private static Stream<Arguments> applyDiscount3Buy1Free() {
        return Stream.of(
                arguments("beer", new BigDecimal(900.0)),     // cart was 1000 became 900  beer(10 % 4 * 50) = 100
                arguments("soap", new BigDecimal(840.0)),     // cart was 900 became 900  soap(10 % 4 * 30) = 60
                arguments("cola", new BigDecimal(800.0)),     // cart was 840 became 900  cola(10 % 4 * 20) = 40
                arguments("beer", new BigDecimal(800.0)),     // discount does not apply product twice
                arguments("soap", new BigDecimal(800.0)),     // discount does not apply product twice
                arguments("cola", new BigDecimal(800.0))      // discount does not apply product twice
        );
    }

    @ParameterizedTest
    @MethodSource("applyDiscount3Buy1Free")
    @Order(4)
    void checkProductApplyDiscount3Buy1FreeInShoppngCart(String productName, BigDecimal priceCartAfterApplyDiscount) {
        cartByTestringDiscaunt3Buy1Free.applyDiscount(new Discount_buy_3_get_1_free(), productName);
        double expected = priceCartAfterApplyDiscount.doubleValue();
        double actual = cartByTestringDiscaunt3Buy1Free.getPrice().doubleValue();
        assertEquals(expected, actual, 0.01);
    }


    /**
     * cartByTestingDiscount30Persent
     * ("beer", 10);
     * ("cola", 10);
     * ("soap", 10);
     */
    // Testing the removal of a different quantity of products from cart
    private static Stream<Arguments> removeAllPrductQuantity() {
        return Stream.of(
                arguments("beer", 11, true),  // 10 < 11 command not working, remains the same quantity beer
                arguments("beer", 9, true),   // 10 < 9 command working, remains 1 product beer
                arguments("beer", 1, false),  // 1 = 1 we remove last beer. Cart contains beer - false.
                arguments("soap", 9, true),    // 10 < 9 command working, remains 1 product soap
                arguments("soap", 1, false),   // 1 = 1 we remove last beer. Cart contains soap - false.
                arguments("cola", 10, false),  // 10 = 10 we remove all cola. Cart contains cala - false.
                arguments("cola", 11, false)   // 0 < 11 we don't have cola in cart. We cannot remove it.
        );
    }

    @ParameterizedTest
    @MethodSource("removeAllPrductQuantity")
    @Order(5)
    void checkRemoveAllPrductQuantity(String productName, int quantity, boolean isProductContainsInProductCart) {
        cartByTestringrRemoveAllPrduct.remove(productName, quantity);
        boolean expected = isProductContainsInProductCart;
        Product product = cartByTestingRemoveMethod.getStorage().getProductByName(productName);
        boolean actual = cartByTestringrRemoveAllPrduct.getCartMap().containsKey(product);
        assertEquals(expected, actual);
    }
}