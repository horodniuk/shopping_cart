package cart;

import discount.Discount;
import discount.DiscountStorage;
import lombok.Getter;
import storage.Storage;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Cart {

    private Storage storage; // Storage containing map of products
    private DiscountStorage discountStorage; // discount register containing discount value and map of discounts
    @Getter
    private Map<Product, Integer> cartMap;         // map of products, which are added in the cart
    private BigDecimal price = new BigDecimal(00.00).setScale(2); // total price of products (including discount)


    /**
     * When creating the constructor of cart we fill the storageMap with products from the storage to check the name
     * of products and their quantity before adding them in the cart.
     */
    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storage = storage;
        this.discountStorage = new DiscountStorage();
    }

    /**
     * Method description
     * method parameters - name of product, and it's quantity
     * Method should add products in the cart
     * we check if product with such name and in needed quantity exists in the storage (with method isProductAvailable()
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

    /**
     * Method description
     * Method parameters - instance of class product;
     * checking if product exists as key in Cart map and if cartMap is not empty.
     * If it doesn't exist then we return false.
     * If exists then we return true.
     */
    private boolean isProductExistInCart(Product product) {
        return !cartMap.isEmpty() && cartMap.containsKey(product);
    }

    /**
     * Method description
     * method parameters - name of product and quantity to be removed;
     * method should reduce product amount in cart and change discount value on this product in discountStorage if
     * discount on this product was applied;
     * We create temporary instance of Product - tempProduct;
     * next we check if such product exists in cart. If true :
     * we create int quantity of this product and start method remove();
     * if false: we output message to console - that such product doesn't exist in cart.
     */
    public void remove(String productName, int quantity) {
        Product product = storage.getProductByName(productName);
        if (isProductExistInCart(product)) {
            int quantityProductInCart = cartMap.get(product);
            remove(product, quantity, quantityProductInCart);
        } else System.out.println("You don't have " + product + " in cart. Please enter another Product.");
    }

    /**
     * Method description
     * first we check if quantity of this product in cart is bigger than quantity needed to remove. If true:
     * we start method reduceProductAndDiscount();
     * if false - we check next statement.
     * next we check if quantity of this product in cart equals quantity needed to remove. If true:
     * we start method deleteProductAndDiscount();
     * if false - we output message that cart doesn't contain enough quantity of this product.
     */
    private void remove(Product product, int quantity, int quantityInCart) {
        if (quantityInCart > quantity) reduceProductAndDiscount(product, quantity);
        else if (quantityInCart == quantity) deleteProductAndDiscount(product, quantity);
        else System.out.printf("Cart doesn't contain %s " +
                               "in quantity %d right now there is only next quantity: %d%n", product, quantity, quantityInCart);
    }

    /**
     * Method description
     * method deletes product and discount if product is removed from cart;
     * first we check if discount is applied on this product with method isDiscountAppliedOnProduct();
     * if true - we create int quantity of this product, type of discount and value of discount on this product;
     * next we remove discount and reduce discount value with method removeDiscountValueAndType();
     * next we remove product and it's quantity from cartMap;
     * after that we update storage and output data to console with updateAndPrintToConsole();
     */
    private void deleteProductAndDiscount(Product product, int quantity) {
        if (discountStorage.isDiscountAppliedOnProduct(product)) {
            Discount tempDiscountType = discountStorage.getDiscountTypeFromMap(product);
            int quantityInCart = cartMap.get(product);
            BigDecimal tempDiscountValue = tempDiscountType.getDiscount(product, quantityInCart);
            discountStorage.removeDiscountValueAndType(product, tempDiscountValue);
        }
        cartMap.remove(product);
        updateAndPrintToConsole(product, quantity);
    }

    /**
     * Method description
     * method should reduce product quantity and discount amount;
     * first we check if discount is applied on this product with method isDiscountAppliedOnProduct();
     * if true - we create int old quantity of this product, old value of discount on this product;
     * next we start method changeQuantityByReduceProduct() and after that create new value of discount on this product
     * and new int quantity of this product;
     * after that we calculate difference between old discount value and new discount value;
     * next we start method updateDiscountValueAndType();
     * and output message to console that discount changed;
     * if false - we start method changeQuantityByReduceProduct();
     * in the end we start method updateAndPrintToConsole().
     */
    private void reduceProductAndDiscount(Product product, int quantity) {
        if (discountStorage.isDiscountAppliedOnProduct(product)) {
            Discount tempDiscount = discountStorage.getDiscountTypeFromMap(product);
            int oldQuantity = cartMap.get(product);
            BigDecimal oldDiscountProductValue = tempDiscount.getDiscount(product, oldQuantity);
            changeQuantityByReduceProduct(product, quantity);
            int newQuantity = cartMap.get(product);
            BigDecimal newDiscountProductValue = tempDiscount.getDiscount(product, newQuantity);
            BigDecimal difference = oldDiscountProductValue.subtract(newDiscountProductValue);
            discountStorage.updateDiscountValueAndType(product, difference, tempDiscount);
            System.out.printf("discount changed. Details: apply %s by  %s. Discount value - %s $ %n",
                    tempDiscount.getClass().getSimpleName(), product, newDiscountProductValue);
        } else {
            changeQuantityByReduceProduct(product, quantity);
        }
        updateAndPrintToConsole(product, quantity);
    }

    /**
     * Method description
     * updates storage by adding product and quantity in it;
     * next updating total price on all products;
     * and output data to console with removePrintToConsole();
     */
    private void updateAndPrintToConsole(Product product, int quantity) {
        storage.addProduct(product, quantity);
        price = updatePrice();
        removePrintToConsole(product, quantity);
    }

    /**
     * Method description
     * changes quantity of this product by subtraction
     */
    private void changeQuantityByReduceProduct(Product product, int quantity) {
        cartMap.put(product, cartMap.get(product) - quantity);
    }

    /**
     * output data (if product is removed from cart) to the console according to the technical task
     */
    private void removePrintToConsole(Product product, int quantity) {
        System.out.println(quantity + " " + product.getName() + "(s) vas removed");
    }


    /**
     * output data (if product is added in cart) to the console according to the technical task
     */
    private void addPrintToConsole(Product product, int quantity) {
        System.out.println(quantity + " " + product.getName() + "(s) vas added");
    }

    /**
     * data output to console in next format: discount:00.00,price:XX.50
     */
    public void price() {
        System.out.println(String.format("discount:%s, price:%s", discountStorage.getDiscountValue(), price));
        System.out.println(this); // for logging, call of method toString on cart
    }

    /**
     * Method description
     * finishes work and writes changes in StorageMap to Storage file or DataBase
     * method write() - writes changes to the storage (Storage file or DataBase).
     */
    public void finish() {
        storage.write();
        System.out.println("Done!");
    }

    /**
     * Method description
     * calculates total price of products without discounts;
     * return - BigDecimal value of total price of all products in Cart.
     */
    private BigDecimal totalPriceWithoutDiscount() {
        Integer sum = cartMap.entrySet().stream()
                .mapToInt(product -> product.getKey().getPrice()
                        .multiply(new BigDecimal(product.getValue())).intValue())
                .sum();
        return new BigDecimal(sum).setScale(2);
    }

    /**
     * Method description
     * we update total discount value in cart (discount)
     * Method parameters - type of discount and name of product
     * applies discount on product
     * checking if product with such name exists in cart
     * if true - we get discount value (discountProductValue)
     * if discount value not 0, then discount worked and then:
     * then we start private method applyDiscount();
     * if product not fount in cart - than we output message to console - that such product doesn't exist in cart.
     */
    public void applyDiscount(Discount discountType, String productName) {
        Product product = storage.getProductByName(productName);
        if (isProductExistInCart(product)) {
            int quantity = cartMap.get(product);
            BigDecimal newDiscountProductValue = discountType.getDiscount(product, quantity).setScale(2);
            if (newDiscountProductValue.intValue() != 0) {
                applyDiscount(discountType, product, newDiscountProductValue);
            }
        } else System.out.println("Discount cannot be added, because there is no such product in cart!");
    }

    /**
     * Method description
     * Method parameters - type of discount, instance of class product and new Discount Type
     * we apply discount on product;
     * first we check if any discount is applied on this product with method isDiscountAppliedOnProduct();
     * if true - then we create int quantity of this product, oldDiscountValue, newDiscountValue and calculate
     * difference between old and new values of discount;
     * then we start method updateDiscountValueAndType();
     * if false - we start method addDiscountValueAndType();
     * next update total price of products in cart (price)
     * output to console - discount added;
     */
    private void applyDiscount(Discount discountType, Product product, BigDecimal newDiscountProductValue) {
        if (discountStorage.isDiscountAppliedOnProduct(product)) {
            int quantity = cartMap.get(product);
            Discount oldDiscountType = discountStorage.getDiscountTypeFromMap(product);
            BigDecimal oldDiscountProductValue = oldDiscountType.getDiscount(product, quantity).setScale(2);
            BigDecimal difference = oldDiscountProductValue.subtract(newDiscountProductValue);
            discountStorage.updateDiscountValueAndType(product, difference,
                    discountType);
        } else {
            discountStorage.addDiscountValueAndType(product, newDiscountProductValue, discountType);
        }
        price = updatePrice();
        System.out.printf("discount added. Details: apply %s by  %s. Discount value - %s $ %n",
                discountType.getClass().getSimpleName(), product, newDiscountProductValue);
    }

    /**
     * Method description
     * updating total price of products in cart
     * Method should subtract discount value on all products in cart from total price on all products without discount;
     * return - BigDecimal of total price including discount.
     */
    private BigDecimal updatePrice() {
        return totalPriceWithoutDiscount().subtract(discountStorage.getDiscountValue());
    }

    @Override
    public String toString() {
        return "~~~~~~~~~~~~~~~~~  CART (LOG) ~~~~~~~~~~~~~~~~~\n" +
               "cartMap=" + cartMap +
               ",\n storage=" + storage +
               ",\n discountStorage=" + discountStorage +
               ",\n price=" + price +
               ",\n discount=" + discountStorage.getDiscountValue() +
               ",\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
               '\n';
    }
}
