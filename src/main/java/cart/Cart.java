package cart;

import discount.Discount;
import storage.Storage;

import java.math.BigDecimal;
import java.util.*;


public class Cart {
    private Map<String, Product> cartMap;     // контейнер для хранения данных корзины
    private Map<String, Product> storageMap;  // хранится данные о кол-ве продуктов на складе
    private Map<String, BigDecimal> discountMap; // контейнер с названием товаров, к которым применили скидку и значение скидки
    private BigDecimal price = new BigDecimal(00.00).setScale(2);
    private BigDecimal discount = new BigDecimal(00.00).setScale(2);

    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storageMap = storage.getStorage();
        this.discountMap = new HashMap<>();
    }

    /*
     * Описание метода
     * параметры метода - назвнание продукта и колличество
     * проверяем есть ли товар с таким названием и в нужном кол-ве на складе
     * tempPrice - временная переменная для хранения цены продукта
     * проверяем если корзина не пуста и там хранится продукт с данным названием - обновляем его кол-во
     * в противоположном случае добавляем продукт в корзину
     * метод printToConsole() - выводим данные на консоль
     * метод updatePrice() - обновляем price (общую стоимость корзины)
     */
    public void add(String productName, int quantity) {
        if (checkProductAndQuantitiInStorage(productName, quantity)) {
            BigDecimal tempPrice = storageMap.get(productName).getPrice();
            if (!cartMap.isEmpty() && cartMap.containsKey(productName)) {
                int newQuantiti = cartMap.get(productName).getQuantity() + quantity;
                cartMap.put(productName, new Product(productName, tempPrice, newQuantiti));
            } else {
                cartMap.put(productName, new Product(productName, tempPrice, quantity));
            }
            printToConsole(quantity, productName);
            price = updatePrice();
        };
    }

    private void printToConsole(int quantity, String productName) {
        System.out.println(quantity + " " + productName + "(s) vas added");
    }

    // вывод на консоль в формате discount:00.00,price:XX.50
    public void price() {
        System.out.println(String.format("discount:%s, price:%s", discount, price));
    }

    public BigDecimal totalPriceWithoutDiscont() {
        List<Product> list = new ArrayList<>(cartMap.values());
        var sum = BigDecimal.ZERO;
        for (Product product : list) {
            sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return sum.setScale(2);
    }
    /*
     * Описание метода
     * параметры метода - Тип скидки и название продукта
     * проверяем есть ли товар с таким названием и в нужном кол-ве на складе и есть ли в корзине
     * если есть получаем значение скидки (discountProductValue)
     * если значение скидки не 0, значит скидка сработала тогда:
     * обновляем общую сумму скидки в корзине (discount)
     * обновляем сумму в корзине (price)
     * добавляем в карту со скидками название продукта и скидку (BigDecimal) на него.
     *  выводим в консоль что скидка добавлена
     */
    public void applyDiscount(Discount discountType, String productName){
       if (isProductExistsInCart(productName) && checkProductAndQuantitiInStorage(productName, 1)){
           BigDecimal discountProductValue = discountType.getDiscount(productName, cartMap);
           if (discountProductValue.intValue() != 0){
               discount = updateDiscount(productName, discountProductValue);
               price = updatePrice();
               discountMap.put(productName, discountProductValue);
               System.out.println("discount added");
           }
       }
    }

    /*
     * Описание метода
     * параметры метода - назвнание продукта и цена скидки на продукт
     * проверяем есть ли название товара, как ключ в карте со скидками.
     * Если есть то значит на данный товар применялась скидка,
     * нужно старую скидку (oldDiscountValueProduct) отминусовать с общей скидки в корзине(discount)
     * так как по заданию нам нужно вы
     * return - плюсуем скидку на продукт в общую сумму скидок
     */
    private BigDecimal updateDiscount(String productName, BigDecimal discountProductValue) {
        if (discountMap.containsKey(productName)){
           BigDecimal oldDiscountValueProduct = discountMap.get(productName);
           discount = discount.subtract(oldDiscountValueProduct);
        }
           return discount.add(discountProductValue);
    }

    private BigDecimal updatePrice() {
        return totalPriceWithoutDiscont().subtract(discount);
    }

    private boolean isProductExistsInCart(String productName) {
        if (!cartMap.containsKey(productName)) {
            System.out.println("Товара " + productName + " нет в корзине. Поэтому на него нельзя применить скидку");
            return false;
        } else {
            return true;
        }
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

    public Map<String, BigDecimal> getDiscountMap() {
        return discountMap;
    }

    @Override
    public String toString() {
        return "Cart{" +
               "cartMap=" + cartMap +
               '}';
    }
}
