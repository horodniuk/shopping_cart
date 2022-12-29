package cart;

import storage.Storage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map <String, Product> storageMap;
    private Map <String, Product> cartMap;
    public Cart(Storage storage) {
        this.cartMap = new HashMap<>();
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

    public void price(){
        // example discount:00.00,price:XX.50
    }

    private boolean checkStorage(String productName, int quantiti) {
        // проверить если вообще такой товар
        // проверить наличие на складе есть есть true, удаляем со склада,
        // если нет выводим в консоль, что не хватает кол-ва - возвращаем false
        return true;
    }

    public Map<String, Product> getStorageMap() {
        return storageMap;
    }

    public Map<String, Product> getCartMap() {
        return cartMap;
    }

}
