package cart;

import discount.Discount;
import storage.Storage;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private Storage storage; // Storage containing map of products
    private Map<String, Product> cartMap;         // map of products, which are added in the cart
    private Map<String, BigDecimal> discountMap;  // map of products, which are added in the cart and discounts applied
    private Map<String, Discount> discountTypesMap; // map of discount types, which are applied on products in Cart
    private BigDecimal price = new BigDecimal(00.00).setScale(2); // total price of products in cart (including discount)
    private BigDecimal discount = new BigDecimal(00.00).setScale(2); // total amount of discount on products in cart


    /**
     * When creating the constructor of cart we fill the storageMap with products from the storage to check the name
     * of products and their quantity before adding them in the cart.
     */
    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storage = storage;
        this.discountMap = new HashMap<>();
        this.discountTypesMap = new HashMap<>();
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
        if (storage.isProductAvailable(productName, quantity)) {
            BigDecimal tempPrice = storage.getProductPrice(productName);
            cartMap.compute(productName, (key, product) ->
                    (isProductExistInCart(productName) ?
                            new Product(productName, tempPrice, cartMap.get(productName).getQuantity() + quantity) :
                            new Product(productName, tempPrice, quantity)));
            addPrintToConsole(quantity, productName);
            storage.removeProduct(productName, quantity);
            price = updatePrice();
        }
    }

    private boolean isProductExistInCart(String productName) {
        return !cartMap.isEmpty() && cartMap.containsKey(productName);
    }

    /*
     * Method description - it should remove products from cart
     * method parameters - name of product, and it's quantity
     * we check if product with such name and in needed quantity exists in the cart
     * otherwise we print message to console - that this product doesn't exist in cart
     *
     * Further we are checking: if quantity of products equals quantity stored in cart, then
     * we create tempProduct - temporary variable for storing product
     * we start method removePrintToConsole(), remove this product from cart and adding it in storage.
     * further we are checking if quantity of products lesser than quantity stored in cart
     * if true - then we reduce amount of this product in cart and adding this amount to storage.
     * otherwise we print message to console, that cart doesn't contain this product in needed quantity.
     * method printToConsole() - we print data to console
     * updateQuantityProductsInStorageMap() - we update quantity of product in storage
     * method updatePrice() - we update price (total price of all products in cart)
     */
    public void remove(String productName, int quantity) {
        if (cartMap.containsKey(productName)) {
            if (cartMap.get(productName).getQuantity() == quantity) {
                deleteProductAndDiscount(productName, quantity);
            } else if (cartMap.get(productName).getQuantity() > quantity) {
                reduceProductAmountAndDiscount(productName, quantity);
            } else {
                System.out.printf("Cart doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                        productName, quantity, cartMap.get(productName).getQuantity());
            }
        } else System.out.println("You don't have " + productName + " in cart. Please enter another Product.");
    }

    public void deleteProductAndDiscount(String productName, int quantity) {
        Product tempProduct = cartMap.get(productName);
        removePrintToConsole(quantity, productName);
        cartMap.remove(productName);
        storage.addProduct(tempProduct, quantity);
        if (discountTypesMap.containsKey(productName)) {
            discount = discount.subtract(discountMap.get(productName));
            discountMap.remove(productName);
            discountTypesMap.remove(productName);
        }
        price = updatePrice();
    }

    public void reduceProductAmountAndDiscount(String productName, int quantity) {
        Product tempProduct = cartMap.get(productName);
        removePrintToConsole(quantity, productName);
        cartMap.get(productName).setQuantity(cartMap.get(productName).getQuantity() - quantity);
        storage.addProduct(tempProduct, quantity);
        price = updatePrice();
        if (discountTypesMap.containsKey(productName)) {
            Discount tempDiscount = discountTypesMap.get(productName);
            BigDecimal discountProductValue = tempDiscount.getDiscount(productName, cartMap).setScale(2);
            if (discountProductValue.intValue() != 0) {
                discount = updateDiscount(productName, discountProductValue);
                price = updatePrice();
                discountMap.put(productName, discountProductValue);
                System.out.printf("discount added. Details: apply %s by  %s. Discount value - %s $ %n",
                        tempDiscount.getClass().getSimpleName(), productName, discountProductValue);
            }
        }
    }

    // output data (if product is added in cart) to the console according to the technical task
    private void addPrintToConsole(int quantity, String productName) {
        System.out.println(quantity + " " + productName + "(s) vas added");
    }

    // output data (if product is removed from cart) to the console according to the technical task
    private void removePrintToConsole(int quantity, String productName) {
        System.out.println(quantity + " " + productName + "(s) vas removed");
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
        List<Product> list = new ArrayList<>(cartMap.values());
        var sum = BigDecimal.ZERO;
        for (Product product : list) {
            sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return sum.setScale(2);
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
        if (isProductExistsInCart(productName)) {
            BigDecimal discountProductValue = discountType.getDiscount(productName, cartMap).setScale(2);
            if (discountProductValue.intValue() != 0) {
                discount = updateDiscount(productName, discountProductValue);
                price = updatePrice();
                discountMap.put(productName, discountProductValue);
                updateDiscountType(productName, discountType);
                System.out.printf("discount added. Details: apply %s by  %s. Discount value - %s $ %n",
                        discountType.getClass().getSimpleName(), productName, discountProductValue);
            }
        }
    }

    private void updateDiscountType(String productName, Discount discountType) {
        if (discountTypesMap.containsKey(productName)) {
            discountTypesMap.remove(productName);
            discountTypesMap.put(productName, discountType);
        } else discountTypesMap.put(productName, discountType);
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
    private BigDecimal updateDiscount(String productName, BigDecimal discountProductValue) {
        if (discountMap.containsKey(productName)) {
            BigDecimal oldDiscountValueProduct = discountMap.get(productName);
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

    /**
     * Method description
     * Method parameters - name of product
     * checking if name of product exists as key in Cart map.
     * If it doesn't exist then we return false and outputs message to the console.
     * If exists then we return true.
     */
    private boolean isProductExistsInCart(String productName) {
        if (!cartMap.containsKey(productName)) {
            System.out.println("Product " + productName + " doesn't exist in cart. " +
                    "Therefore discount cannot be applied.");
            return false;
        } else {
            return true;
        }
    }


    public Storage getStorage() {
        return storage;
    }

    public Map<String, Product> getCartMap() {
        return cartMap;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Map<String, BigDecimal> getDiscountMap() {
        return discountMap;
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
