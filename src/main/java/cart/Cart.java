package cart;

import storage.Storage;

import java.math.BigDecimal;
import java.util.*;

/**
 * класс Корзина нужен как контейнер для хранения и работы с данными
 */
public class Cart {
    private Map <String, Product> storageMap; // контейнер для хранения данных корзины
    private Map <String, Product> cartMap;    // хранится данные о кол-ве продуктов на складе
    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storageMap = storage.getStorage();
    }

    public void add(String productName, int quantiti){
       if (checkStorage(productName, quantiti)) {
           BigDecimal price = storageMap.get(productName).getPrice();
           if (!cartMap.isEmpty() && cartMap.containsKey(productName)) {
               int newQuantiti = cartMap.get(productName).getQuantiti() + quantiti;
               cartMap.put(productName, new Product(productName, price, newQuantiti));
               printToConsole(quantiti, productName);
           } else {
               cartMap.put(productName, new Product(productName, price, quantiti));
               printToConsole(quantiti, productName);
           }
       };
    }

    private void printToConsole(int quantiti, String productName) {
        System.out.println(quantiti +" "+ productName + " vas added");
    }

    public BigDecimal totalPrice() {
        List<Product> list = new ArrayList<>(cartMap.values());
        return list.stream()
                .map(x -> x.getPrice().multiply(x.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    /**
     * в разработке
     */
    public void price(){
        // example discount:00.00,price:XX.50
    }

    /**
     *  Задача: имплементировать метод checkStorage
     *  проверить если ли товар с таким названием на складе
     *  проверить наличие на складе есть есть true, удаляем со склада,
     *  если нет выводим в консоль, что не хватает кол-ва - возвращаем false
     */
    private boolean checkStorage(String productName, int quantiti) {
        return true;
    }

    public Map<String, Product> getStorageMap() {
        return storageMap;
    }

    public Map<String, Product> getCartMap() {
        return cartMap;
    }

}
