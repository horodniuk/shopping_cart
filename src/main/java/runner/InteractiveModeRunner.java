package runner;

import cart.Cart;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;
import storage.StorageWithJson;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * В этом класе пользователь вводит команды в консоль а наша задача считать эте команды
 * и если такие команды существуют - выполнить их. (смотреть метод parseCommandLine())
 * предворительно работаем с корзиной
 */
public class InteractiveModeRunner implements ModeRunner {
    private String pathToStorage;

    public InteractiveModeRunner(String pathToStorage) {
        this.pathToStorage = pathToStorage;
    }

    /**
     * Задача
     * Нужно реализовать метод parseCommandLine который в зависимости от комманды будет
     * работать с корзиной и выводить реузьтаты в консоль
     */
    public void start() {
        System.out.println("Запускаем режим Interactive mode.");
        showTooltipWithCommands();
        System.out.println("Введите команду в консоль:");
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        while (true) {
            String commandStr = new Scanner(System.in).nextLine();
            if (commandStr.equals("finish")) return;
            parseCommandLine(commandStr, cart);
        }
    }

    /**
     * Сейчас прописан код в котором данные указаны напрямую
     * Это для демонстрации работы
     * Нужно прописать метод что он работал со всеми командами которые указаны в тех. задании
     * Например: Если навход подается строка "add bear 5" нужно распарсить для получения
     * названия продукта"bear" и кол-ва "5" и проверить
     * есть ли такой продукт,
     * есть ли его достаточное кол-во и после выполнить команду, например  cart.add("bear", 5)
     */
    @Override
    public void parseCommandLine(String line, Cart cart) {
        String[] lineArray = line.split(" ");

        switch (lineArray[0]) {
            case "add":
                cart.add(lineArray[1], Integer.parseInt(lineArray[2]));
                break;
            case "price":
                cart.price();
                break;
            case "discount":
                if (lineArray[1].equals("buy_3_get_1_free")) {
                    cart.applyDiscount(new Discount_BUY_3_GET_1_FREE(), lineArray[2]);
                } else {
                    cart.applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), lineArray[2]);
                }
                break;
            default:
                System.out.println("неизвесная команда, попробуйте еще раз, например \"add bear 5\"");
        }
    }

    private void showTooltipWithCommands() {
        System.out.println("\"price\" - вывести сумму товара");
        System.out.println("\"add bear 5\" - добавить товар в корзину. Структура: add [название продукта] [кол-во продукта]");
        System.out.println("\"discount buy_1_get_30_percentage cola\" - применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.println("\"discount buy_3_get_1_free bear\" - применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.println("\"finish\" - завершить работу");
    }
}
