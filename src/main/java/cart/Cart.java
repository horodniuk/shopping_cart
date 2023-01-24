package cart;

import discount.Discount;
import storage.Storage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private Storage storage; // Storage containing map of products
    private Map<String, Product> cartMap;         // map of products, which are added in the cart
    private Map<String, Discount> discountMap;    // map of discount types, which are applied on products in Cart
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
        if (storage.isProductAvailable(productName, quantity)) {
            BigDecimal tempPrice = storage.getProductPrice(productName);
            cartMap.compute(productName, (key, product) ->
                    (isProductExistInCart(productName) ?
                            new Product(productName, tempPrice, cartMap.get(productName).getQuantity()
                                    + quantity) :
                            new Product(productName, tempPrice, quantity)));
            addPrintToConsole(quantity, productName);
            storage.removeProduct(productName, quantity);
            price = updatePrice();
        }
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
    public void remove(String productName, int neededQuantity) {
        boolean isProductInCart = cartMap.containsKey(productName);
        if (isProductInCart) {
            List<ProductQuantityState> productQuantityStates = new ArrayList<>();
            productQuantityStates.add(new ProductQuantityStateEquals());
            productQuantityStates.add(new ProductQuantityStateSmaller());
            productQuantityStates.add(new ProductQuantityStateMore());
            int quantityInCart = cartMap.get(productName).getQuantity();
            for (ProductQuantityState productState : productQuantityStates) {
                if (productState.checkProductQuantity(quantityInCart,neededQuantity)){
                    productState.invokeAction(productName,quantityInCart,neededQuantity,this);
                }
            }
        } else System.out.println("You don't have " + productName + " in cart. Please enter another Product.");
    }

    public void deleteProductAndDiscount(String productName, int quantity) {
        if (discountMap.containsKey(productName)) {
            discount = discount.subtract(discountMap.get(productName).getDiscount(productName, cartMap));
            discountMap.remove(productName);
        }
        Product tempProduct = cartMap.get(productName);
        removePrintToConsole(quantity, productName);
        cartMap.remove(productName);
        storage.addProduct(tempProduct, quantity);
        price = updatePrice();
    }

    public void reduceProductAndDiscount(String productName, int quantity) {
        Product tempProduct = cartMap.get(productName);
        if (discountMap.containsKey(productName)) {
            Discount tempDiscount = discountMap.get(productName);
            BigDecimal discountProductValue = tempDiscount.getDiscount(productName, cartMap).setScale(2);
            changeQuantity(productName, quantity);
            discount = discount.subtract(discountProductValue).add(tempDiscount.getDiscount(productName, cartMap)).
                    setScale(2);
            discountMap.put(productName, tempDiscount);
            System.out.printf("discount changed. Details: apply %s by  %s. Discount value - %s $ %n",
                    tempDiscount.getClass().getSimpleName(), productName, discountProductValue);
        } else {
            changeQuantity(productName, quantity);
        }
        storage.addProduct(tempProduct, quantity);
        price = updatePrice();
        removePrintToConsole(quantity, productName);
    }

    private void changeQuantity(String productName, int quantity) {
        cartMap.get(productName).setQuantity(cartMap.get(productName).getQuantity() - quantity);
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
        try {
            storage.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (isProductExistInCart(productName)) {
            BigDecimal discountProductValue = discountType.getDiscount(productName, cartMap).setScale(2);
            if (discountProductValue.intValue() != 0) {
                discount = updateDiscount(productName, discountProductValue);
                price = updatePrice();
                discountMap.put(productName, discountType);
                System.out.printf("discount added. Details: apply %s by  %s. Discount value - %s $ %n",
                        discountType.getClass().getSimpleName(), productName, discountProductValue);
            }
        } else System.out.println("Product " + productName + " doesn't exist in cart. " +
                "Therefore discount cannot be applied.");
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
            BigDecimal oldDiscountValueProduct = discountMap.get(productName).getDiscount(productName, cartMap);
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

    private boolean isProductExistInCart(String productName) {
        return !cartMap.isEmpty() && cartMap.containsKey(productName);
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

    public Map<String, Discount> getDiscountMap() {
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
