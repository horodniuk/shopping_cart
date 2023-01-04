package runner;

import cart.Cart;
import cart.CartCommandParser;
import cart.Product;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;
import storage.StorageWithJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Считываем построчно команды с файла и если такие команды существуют -
 * выполняем их. (смотреть метод excecuteCommand())
 */
public class FileModeRunner implements ModeRunner {
    private String pathToStorage;
    private String pathToCommand;

    public FileModeRunner(String pathToStorage, String pathToCommand) {
        this.pathToStorage = pathToStorage;
        this.pathToCommand = pathToCommand;
    }

    /**
     * Считываем построчно команды из файла.
     */
    @Override
    public void start() {
        System.out.println("Запускаем режим File mode." +
                           " Команды будут считываться с файла\" " + pathToCommand);
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(pathToCommand));
            String line = reader.readLine();
            while (line != null) {
                if (line.length() > 0) excecuteCommand(line, cart);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * выполнено
     * Задача
     * Нужно прописать метод который будет выполнять команды, которые указаны в тех.задании
     * Например: Если на вход подается строка "add beer 5" нужно распарсить для получения
     * названия продукта"beer" и кол-ва "5" и проверить есть ли такой продукт,
     * есть ли его достаточное кол-во и после выполнить команду.
     * например:
     * add beer 5 --> cart.add("beer", 5)
     * add soap 2 --> cart.add("soap", 2)
     * discount buy_1_get_30_percentage beer --> applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), "beer")
     * discount buy_3_get_1_free soap --> applyDiscount(new Discount_BUY_3_GET_1_FREE(), "soap")
     *
     * тестовый лист с командами создан в resources по адресу sourse root --> commadsList.txt
     */
    @Override
    public void excecuteCommand(String line, Cart cart) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        if (cartCommandParser.parse(line)) return;
        if ((line.equals("price"))) {
            cart.price();
        } else {
            System.out.println("неизвестная команда - " + line);
        }
    }

    public String getPathToCommand() {
        return pathToCommand;
    }
}
