package storage;

import cart.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * реализация склада с товарами на основе файла json
 *
 */
public class StorageWithJson implements Storage {
    private String file;
    private Map<String, Product> storageProducts;

    public StorageWithJson(String file) {
        this.file = file;
        this.storageProducts = load(file);
    }

    /**
     * Задача: переписать метод load() с проверками и написать к нему тесты
     * Сейчас он заполнен напрямую для демонстрации работы
     * нужно что бы на выходе мы получили Map c данными которые хранятся в resources по адресу
     * sourse root -- > shopping_products_storage.json
     *
     * доп.cсылки
     * https://www.youtube.com/watch?v=YKUqIo7iXtA
     * и еще
     * https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
     */
    @Override
    public Map<String, Product> load(String file) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Product> products = null;
        try {
            products = objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
//        products.put("bear", new Product("bear", new BigDecimal(50.0), 30));
//        products.put("cola", new Product("cola", new BigDecimal(20.0), 20));
//        products.put("soap", new Product("soap", new BigDecimal(30.0), 10));

        return products;
    }

    /**
     * Эту задачу нужно уточнить
     * Задача: имплементировать метод write() с проверками и написать к нему тесты
     * Сейчас он заполнен напрямую для демонстрации работы
     * данные со склада Map нужно записать в файл, которые хранятся в resources по адресу
     * sourse root --> shopping_products_storage.json
     *
     * доп.cсылки
     * https://www.geeksforgeeks.org/how-to-convert-map-to-json-to-hashmap-in-java/
     */
    @Override
    public void write(Map<String, Product> storage) {
        System.out.println("обновить файл json");
    }


    @Override
    public Map<String, Product> getStorage() {
        return storageProducts;
    }

    @Override
    public String toString() {
        return "StorageWithJson{" +
               "storageProducts=" + storageProducts +
               '}';
    }
}
