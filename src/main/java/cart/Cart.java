package cart;

import discount.Discount;
import storage.Storage;

import java.math.BigDecimal;
import java.util.*;


public class Cart {
    private Map<String, Product> cartMap;     // контейнер для хранения данных корзины
    private Map<String, Product> storageMap;  // хранится данные о кол-ве продуктов на складе
    private BigDecimal price = new BigDecimal(00.00).setScale(2);
    private BigDecimal discount = new BigDecimal(00.00).setScale(2);

    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storageMap = storage.getStorage();
    }

    // example adding: 5 bear was added
    public void add(String productName, int quantiti) {
        if (checkProductAndQuantitiInStorage(productName, quantiti)) {
            BigDecimal tempPrice = storageMap.get(productName).getPrice();
            if (!cartMap.isEmpty() && cartMap.containsKey(productName)) {
                int newQuantiti = cartMap.get(productName).getQuantity() + quantiti;
                cartMap.put(productName, new Product(productName, tempPrice, newQuantiti));
                printToConsole(quantiti, productName);
            } else {
                cartMap.put(productName, new Product(productName, tempPrice, quantiti));
                printToConsole(quantiti, productName);
            }
            price = updatePrice();
        };
    }

    private void printToConsole(int quantiti, String productName) {
        System.out.println(quantiti + " " + productName + "(s) vas added");
    }

    // вывод на консоль в формате discount:00.00,price:XX.50
    public void price() {
        System.out.printf("discount:%s, price:%s", discount, price);
    }

    public BigDecimal totalPriceWithoutDiscont() {
        List<Product> list = new ArrayList<>(cartMap.values());
        var sum = BigDecimal.ZERO;
        for (Product product : list) {
            sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return sum.setScale(2);
    }

    public void applyDiscount(Discount discountType, String productName){
       if (isProductExistsInCart(productName) && checkProductAndQuantitiInStorage(productName, 1)){
           BigDecimal tempDiscount = discountType.getDiscount(productName, cartMap);
           if (tempDiscount.intValue() != 0){
             //  System.out.println("tempDiscount " + tempDiscount);
               discount = discount.add(tempDiscount);
               price = updatePrice();
               System.out.println("discount added");
           }
       }
    }

    private BigDecimal updatePrice() {
        return totalPriceWithoutDiscont().subtract(discount);
    }

    private boolean isProductExistsInCart(String productName) {
        return cartMap.containsKey(productName);
    }



    /**
     * Задача: имплементировать метод checkStorage
     * проверить если ли товар с таким названием на складе
     * проверить наличие на складе есть есть true, удаляем со склада,
     * если нет выводим в консоль, что не хватает кол-ва - возвращаем false
     * Сейчас метод просто проверяет есть товар на складе или нет
     */
    private boolean checkProductAndQuantitiInStorage(String productName, int quantiti) {
        return storageMap.containsKey(productName);
    }

    public Map<String, Product> getStorageMap() {
        return storageMap;
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

    @Override
    public String toString() {
        return "Cart{" +
               "cartMap=" + cartMap +
               '}';
    }
}
