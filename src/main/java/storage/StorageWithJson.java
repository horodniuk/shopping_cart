package storage;

import cart.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * реализация склада с товарами на основе файла json
 */
public class StorageWithJson implements Storage {
    private File file;
    private Map<String, Product> storageProducts;

    public StorageWithJson(File file) {
        this.file = file;
        this.storageProducts = load(file);
    }

    /**
     * Задача: переписать метод load() с проверками и написать к нему тесты
     * Сейчас он заполнен напрямую для демонстрации работы
     * нужно что бы на выходе мы получили Map c данными которые хранятся в resources по адресу
     * sourse root -- > shopping_products_storage.json
     * <p>
     * доп.cсылки
     * https://www.youtube.com/watch?v=YKUqIo7iXtA
     * и еще
     * https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
     */
    @Override
    public Map<String, Product> load(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> productList;
        Map<String, Product> productMap = new LinkedHashMap<>();
        try {
            productList = objectMapper.readValue(file, new TypeReference<>() {
            });
            for (Product product : productList) {
                productMap.put(product.getName(), product);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return productMap;
    }

    /**
     * Эту задачу нужно уточнить
     * Задача: имплементировать метод write() с проверками и написать к нему тесты
     * Сейчас он заполнен напрямую для демонстрации работы
     * данные со склада Map нужно записать в файл, которые хранятся в resources по адресу
     * sourse root --> shopping_products_storage.json
     * <p>
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
