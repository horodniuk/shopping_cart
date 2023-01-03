package storage;

import cart.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.util.Map;

/*
 * метод load() загружает данные данные в нашу карту (Map)
 * если парсим файл json - получаем контейнер Map c заполнеными данными с этого файла,
 * если парсим файл с подключением к базе данных - там своя будет реализация,
 * но на выходе также будет Map заполнена из базы данных (это не точно)
 *
 * метод write() - записываем данные обратно в файл после изменений
 * метод getStorage() - получение карты с данными
 */
public interface Storage {
   Map<String, Product> load(String file);
   void write (Map<String, Product> storage);
   Map<String, Product> getStorage();
}
