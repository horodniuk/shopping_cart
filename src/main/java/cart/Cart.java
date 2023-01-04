package cart;

import discount.Discount;
import storage.Storage;

import java.math.BigDecimal;
import java.util.*;


public class Cart {
    private Map<String, Product> cartMap;         // карта продуктов, которые добавили в корзину
    private Map<String, Product> storageMap;      // карта продуктов, которые на складе (storage)
    private Map<String, BigDecimal> discountMap;  // карта продуктов, к которым добавили в корзину и приминили скидку
    private BigDecimal price = new BigDecimal(00.00).setScale(2);    // общая сумма товаров в корзине (скидка учитываеться)
    private BigDecimal discount = new BigDecimal(00.00).setScale(2); // общая сумма скидки в корзине

    /**
     * При создании конструктора корзины сразу заполняем карту (storageMap), для продуктов которые на складе
     * для того чтобы проверять наличие и название продукта перед добавлением в корзину.
     */
    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storageMap = storage.getStorage();
        this.discountMap = new HashMap<>();
    }

    /*
     * Описание метода - должен добавлять товар в корзину
     * параметры метода - название продукта и колличество
     * проверяем есть ли товар с таким названием и в нужном кол-ве на складе
     * tempPrice - временная переменная для хранения цены продукта
     * проверяем если корзина не пуста и там хранится продукт с данным названием - обновляем его кол-во
     * в противоположном случае добавляем продукт в корзину
     * метод printToConsole() - выводим данные на консоль
     * updateQuantityProductsInStorageMap() - обновили кол-во продукта на складе
     * метод updatePrice() - обновляем price (общую сумму товаров в корзине)
     */
    public void add(String productName, int quantity) {
        if (checkProductAndQuantityInStorage(productName, quantity)) {
            BigDecimal tempPrice = storageMap.get(productName).getPrice();
            if (!cartMap.isEmpty() && cartMap.containsKey(productName)) {
                int newQuantity = cartMap.get(productName).getQuantity() + quantity;
                cartMap.put(productName, new Product(productName, tempPrice, newQuantity));
            } else {
                cartMap.put(productName, new Product(productName, tempPrice, quantity));
            }
            printToConsole(quantity, productName);
            updateQuantityProductsInStorageMap(productName, quantity);
            price = updatePrice();
        }
    }

    // вывод на консоль по тех.заданию
    private void printToConsole(int quantity, String productName) {
        System.out.println(quantity + " " + productName + "(s) vas added");
    }

    /**
     * вывод на консоль в формате discount:00.00,price:XX.50
     */
    public void price() {
        System.out.println(String.format("discount:%s, price:%s", discount, price));
        System.out.println(this); // для логирования, вызов метода toString на корзине
    }

    /**
     * сумма товаров без учета скидок
     */
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
     * проверяем есть ли товар с таким названием в корзине
     * если есть получаем значение скидки (discountProductValue)
     * если значение скидки не 0, значит скидка сработала тогда:
     * обновляем общую сумму скидки в корзине (discount)
     * обновляем сумму в корзине (price)
     * добавляем в карту со скидками название продукта и скидку (BigDecimal) на него.
     * выводим в консоль - скидка добавлена
     */
    public void applyDiscount(Discount discountType, String productName) {
        if (isProductExistsInCart(productName)) {
            BigDecimal discountProductValue = discountType.getDiscount(productName, cartMap).setScale(2);
            if (discountProductValue.intValue() != 0) {
                discount = updateDiscount(productName, discountProductValue);
                price = updatePrice();
                discountMap.put(productName, discountProductValue);
                System.out.printf("discount added. Details: apply %s by  %s. Discont value - %s $ %n",
                        discountType.getClass().getSimpleName(), productName, discountProductValue);
            }
        }
    }

    /**
     * Описание метода
     * параметры метода - название продукта и цена скидки на продукт
     * проверяем есть ли название товара, как ключ в карте со скидками.
     * Если есть то значит на данный товар применялась скидка,
     * нужно старую скидку (oldDiscountValueProduct) отминусовать из общей скидки в корзине(discount)
     * так как по тех.заданию - если на один продукт применяется две скидки - то применить только последнюю
     * return - плюсуем скидку на продукт в общую сумму скидок
     */
    private BigDecimal updateDiscount(String productName, BigDecimal discountProductValue) {
        if (discountMap.containsKey(productName)) {
            BigDecimal oldDiscountValueProduct = discountMap.get(productName);
            discount = discount.subtract(oldDiscountValueProduct);
        }
        return discount.add(discountProductValue).setScale(2);
    }

    /**
     * обновили общую сумму товаров в корзине
     */
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
     * обновили кол-во продукта на складе
     */
    private void updateQuantityProductsInStorageMap(String productName, int quantity) {
        storageMap.get(productName).setQuantity(storageMap.get(productName).getQuantity() - quantity);
    }


    /**
     * выполнено
     * Задача: имплементировать метод checkProductAndQuantityInStorage
     * проверить если ли товар с таким названием на складе
     * проверить наличие на складе, если есть возращаем true,
     * если нет -  выводим в консоль,сообщение что нехватает кол-ва - возвращаем false
     */
    private boolean checkProductAndQuantityInStorage(String productName, int quantity) {
        if (storageMap.get(productName).getQuantity() < quantity) {
            System.out.printf("На складе отсутствует %s в количестве %d на данный момент есть следующей количество: %d%n",
                    productName, quantity, storageMap.get(productName).getQuantity());
        }
        return storageMap.get(productName).getQuantity() >= quantity;
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
        return "~~~~~~~~~~~~~~~~~  КОРЗИНА (LOG) ~~~~~~~~~~~~~~~~~\n" +
               "cartMap=" + cartMap +
               ",\n storageMap=" + storageMap +
               ",\n discountMap=" + discountMap +
               ",\n price=" + price +
               ",\n discount=" + discount +
               ",\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
               '\n';
    }
}
