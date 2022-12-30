package runner;

import cart.Cart;
import storage.StorageWithJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * В этом класе мы считываем данные с файла команд и выводим результат на консоль
 * предворительно работаем с корзиной
 */


public class FileModeRunner implements ModeRunner {
    private String pathToStorage;
    private String pathToCommand;

    public FileModeRunner(String pathToStorage, String pathToCommand) {
        this.pathToStorage = pathToStorage;
        this.pathToCommand = pathToCommand;
    }

    /**
     * Описание
     * В File Mode создаем корзину и считываем построчно команды из файла
     *
     *  доп.ссылки
     *  https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line
     *
     * Задача
     * Нужно реализовать метод parseCommandLine() который в зависимости от комманды будет
     * работать с корзиной и выводить реузьтаты в консоль
     *
     */
    @Override
    public void start() {
        System.out.println("Запускаем File mode");
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(pathToCommand));
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                //вместо вывода на консоль строк из файла нужно парсить каждую строчку
                parseCommandLine(line, cart);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Сейчас прописан код в котором данные указаны напрямую
     * Это для демонстрации работы
     * Нужно прописать метод что он работал со всеми командами которые указаны в тех. задании
     * Например: Если навход подается строка "add bear 5" нужно распарсить для получения
     * названия продукта"bear" и кол-ва "5" и проверить
     * есть ли такой продукт,
     * есть ли его достаточное кол-во и после выполнить команду, например  cart.add("bear", 5)
     *
     *  тестовый лист с командами создан в resources по адресу
     *  sourse root --> commadsList.txt
     */
    @Override
    public void parseCommandLine(String line, Cart cart) {
        if (line.equals("add bear 5")) {
            cart.add("bear", 5);
        }
        if (line.equals("add cola 1")) {
            cart.add("cola", 1);
        }
        if (line.equals("price")) {
            cart.price();
        }
    }
}
