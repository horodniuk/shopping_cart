package runner;

import cart.Cart;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;
import storage.StorageWithJson;

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
        /*
          Переводим все символы строки в нижний регистр и разбиваем строку на массив подстрок.
          lineArray[0] - это название метода, например add(), price() или discount().
          lineArray[1] - это наименование продукта для добавления (в случае запуска метода add()) или наименование
          скидки (в случае запуска метода discount()).
          lineArray[2] - это количество продуктов для добавления в корзику (в случае метода add()) или наименование
          продукта к которому нужно применить скидку (в случае метода discount()).
          в случае если lineArray[0] - add, добавил проверку, является ли lineArray[2] числом.
          в случае если lineArray[0] - discount, добавил проверку, верное ли название скидки находится в lineArray[1].
         */
        String[] lineArray = line.toLowerCase().split(" ");

        switch (lineArray[0]) {
            case "add" -> {
                if (Character.isDigit(lineArray[2].chars().sum())) {
                    cart.add(lineArray[1], Integer.parseInt(lineArray[2]));
                } else System.out.println("Please enter the correct quantity of Product. For example - 5");
            }
            case "price" -> cart.price();
            case "discount" -> {
                if (lineArray[1].equals("buy_3_get_1_free")) {
                    cart.applyDiscount(new Discount_BUY_3_GET_1_FREE(), lineArray[2]);
                }
                if (lineArray[1].equals("buy_1_get_30_percentage")) {
                    cart.applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), lineArray[2]);
                } else System.out.println("You entered the wrong type of discount. Try next command, for example," +
                        "\"discount buy_3_get_1_free soap\"");
            }
            default -> System.out.println("неизвесная команда, попробуйте еще раз, например \"add bear 5\"");
        }
    }
    // update showTooltipWithCommands
    private void showTooltipWithCommands() {
        System.out.printf("%38s\n","prise");
        System.out.printf("%s\n","Bывести сумму товара");
        System.out.printf("%47s\n","add [bear] [5]");
        System.out.printf("%s\n","Структура: add [название продукта] [кол-во продукта]");
        System.out.printf("%68s","discount [buy_3_get_1_free] [beer] ");
        System.out.printf("%2s\n","or discount [buy_1_get_30_percentage] [cola]");
        System.out.printf("%s\n","Применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.printf("%60s\n","finish - (завершить работу)");
    }
}
