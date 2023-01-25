package cart;

import discount.Discount;
import storage.Storage;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;

public class Cart {
    private Storage storage; // Storage containing map of products
    private Map<Product, Integer> cartMap;         // map of products, which are added in the cart
    private Map<Product, Discount> discountMap;    // map of discount types, which are applied on products in Cart
    private BigDecimal price = new BigDecimal(00.00).setScale(2); // total price of products (including discount)
    private BigDecimal discount = new BigDecimal(00.00).setScale(2); // total amount of discount on products in cart


    /**
     * When creating the constructor of cart we fill the storageMap with products from the storage to check the name
     * of products and their quantity before adding them in the cart.
     */
    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storage = storage;
        this.discountMap = new HashMap<>();
    }

    /*
     * Method description - it should add products in the cart
     * method parameters - name of product, and it's quantity
     * we check if product with such name and in needed quantity exists in the storage
     * tempPrice - temporary variable for storing price of product
     * Further we are checking if cart is not empty and stores product with such name - then we update its quantity
     * otherwise we add this product in cart
     * method addPrintToConsole() - we print data to console
     * method storage.removeProduct() - we remove quantity of this product from storage
     * method updatePrice() - we update price (total price of all products in cart)
     */
    public void add(String productName, int quantity) {
        Product product = storage.getProductByName(productName);
        if (storage.isProductAvailable(product, quantity)) {
            cartMap.put(product, isProductExistInCart(product) ? cartMap.get(product) + quantity : quantity);
            addPrintToConsole(product, quantity);
            storage.removeProduct(product, quantity);
            price = updatePrice();
        }
    }

    private boolean isProductExistInCart(Product product) {
        return !cartMap.isEmpty() && cartMap.containsKey(product);
    }

    /*
     * Method description - it should remove products from cart
     * method parameters - name of product, and it's quantity
     * we check if product with such name exists in the cart: if true we check next statements,
     * if false - we output message to the console, that we don't have such product in cart.
     * then we check if quantity of this product in cart equals needed quantity
     * If true then we remove the product from the cart, update total discount and total price
     * and add it in storage with method deleteProductAndDiscount().
     * If false then we check next statement;
     * We check if quantity of the product in cart is bigger than needed:
     * if true then we call method reduceProductAndDiscount() and reduce amount of this product in cart,
     * and reduce total discount and total price;
     * otherwise we print message to console - that cart doesn't contain this product in needed quantity
     *
     * method deleteProductAndDiscount() - we delete product from the cart, update total price and total discount;
     * method reduceProductAndDiscount() - we reduce quantity of product in storage, and update total price
     * and total discount.
     */


    public void remove(String productName, int quantity) {
        Product product = storage.getProductByName(productName);
        if (isProductExistInCart(product)) {
            int quantityProductInCart = cartMap.get(product);
            remove(product, quantity, quantityProductInCart);
        } else System.out.println("You don't have " + product + " in cart. Please enter another Product.");
    }

    private void remove(Product product, int quantity, int quantityInCart) {
        if (quantityInCart > quantity) reduceProductAndDiscount(product, quantity);
        else if (quantityInCart == quantity) deleteProductAndDiscount(product, quantity);
        else System.out.printf("Cart doesn't contain %s " +
           "in quantity %d right now there is only next quantity: %d%n", product, quantity, quantityInCart);
    }



    private void deleteProductAndDiscount(Product product, int quantity) {
        if (discountMap.containsKey(product)) {
            Discount tempDiscount = discountMap.get(product);
            discount = discount.subtract(tempDiscount.getDiscount(product, cartMap));
            discountMap.remove(product);
        }
        cartMap.remove(product);

        storage.addProduct(product, quantity);
        price = updatePrice();
        removePrintToConsole( product, quantity);
    }

    private void reduceProductAndDiscount(Product  product, int quantity) {
        if (discountMap.containsKey(product)) {
            Discount tempDiscount = discountMap.get(product);
            BigDecimal discountProductValue = tempDiscount.getDiscount(product, cartMap);
            changeQuantity(product, quantity);
            discount = discount.subtract(discountProductValue).add(tempDiscount.getDiscount(product, cartMap));
            discountMap.put(product, tempDiscount);
            System.out.printf("discount changed. Details: apply %s by  %s. Discount value - %s $ %n",
                    tempDiscount.getClass().getSimpleName(), product, discountProductValue);
        } else{
            changeQuantity(product, quantity);
        }

        storage.addProduct(product, quantity);
        price = updatePrice();
        removePrintToConsole( product, quantity);
    }



    private void changeQuantity(Product product, int quantity) {
        cartMap.put(product, cartMap.get(product) - quantity);
    }

    // output data (if product is removed from cart) to the console according to the technical task
    private void removePrintToConsole(Product product, int quantity) {
        System.out.println(quantity + " " + product.getName() + "(s) vas removed");
    }


    // output data (if product is added in cart) to the console according to the technical task
    private void addPrintToConsole( Product product, int quantity) {
        System.out.println(quantity + " " + product.getName() + "(s) vas added");
    }

    /**
     * data output to console in next format: discount:00.00,price:XX.50
     */
    public void price() {
        System.out.println(String.format("discount:%s, price:%s", discount, price));
        System.out.println(this); // for logging, call of method toString on cart
    }

    /**
     * finishes work and writes changes in StorageMap to Storage file or DataBase
     */
    public void finish() {
        storage.write();
        System.out.println("Done!");
    }

    /**
     * total price of products without discounts
     */
    public BigDecimal totalPriceWithoutDiscount() {
        Integer sum =  cartMap.entrySet().stream()
                .mapToInt(product -> product.getKey().getPrice()
                        .multiply(new BigDecimal(product.getValue())).intValue())
                .sum();
        return new BigDecimal(sum).setScale(2);
    }

    /*
     * Method description
     * Method parameters - type of discount and name of product
     * checking if product with such name exists in cart
     * if true - we get discount value (discountProductValue)
     * if discount value not 0, then discount worked and then:
     * we update total discount value in cart (discount)
     * then update total price of products in cart (price)
     * next we add in map with discounts product name and discount value on it (BigDecimal).
     * output to console - discount added
     */
    public void applyDiscount(Discount discountType, String productName) {
        Product product = storage.getProductByName(productName);
        if (isProductExistInCart(product)) {
            BigDecimal discountProductValue = discountType.getDiscount(product, cartMap).setScale(2);
            if (discountProductValue.intValue() != 0) {
                discount = updateDiscount(product, discountProductValue);
                price = updatePrice();
                discountMap.put(product, discountType);
                System.out.printf("discount added. Details: apply %s by  %s. Discount value - %s $ %n",
                        discountType.getClass().getSimpleName(), product, discountProductValue);
            }
        }
    }

    /**
     * Method description
     * Method parameters - name of product and amount of discount applied to product
     * checking if name of product exists as key in map with discounts.
     * If true then discount was already applied in this product, then we must subtract old discount
     * (oldDiscountValueProduct) from total discount on all products in cart(discount)
     * Because according to the totalPriceWithoutDiscount - if two discounts are applied to one product -
     * then we must apply only the last one.
     * return - we add discount on this product to total discount on all products.
     */
    private BigDecimal updateDiscount(Product product, BigDecimal discountProductValue) {
        if (discountMap.containsKey(product)) {
            BigDecimal oldDiscountValueProduct = discountMap.get(product).getDiscount(product, cartMap);
            discount = discount.subtract(oldDiscountValueProduct);
        }
        return discount.add(discountProductValue).setScale(2);
    }
    /**
     * updating total price of products in cart
     */
    private BigDecimal updatePrice() {
        return totalPriceWithoutDiscount().subtract(discount);
    }




    public Storage getStorage() {
        return storage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "~~~~~~~~~~~~~~~~~  CART (LOG) ~~~~~~~~~~~~~~~~~\n" +
                "cartMap=" + cartMap +
                ",\n storage=" + storage +
                ",\n discountMap=" + discountMap +
                ",\n price=" + price +
                ",\n discount=" + discount +
                ",\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                '\n';
    }

}
