package runner;

import cart.Cart;
import cart.Product;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;
import storage.StorageWithJson;

import java.util.Map;
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
                if (lineArray.length < 3) {
                    System.out.println("Please enter the correct quantity of Product.");
                    return;
                }
                int quantityProductsNeeded = Integer.parseInt(line.replaceAll("\\D", ""));
                if (checkProductName(cart.getStorageMap(), lineArray[1]) && quantityProductsNeeded != 0) {
                    cart.add(lineArray[1], quantityProductsNeeded);
                } else System.out.println("Please enter the correct quantity and name of Product. " +
                        "For example - 5 and bear");
            }
            case "price" -> cart.price();
            case "discount" -> {
                if (lineArray[1].equals("buy_3_get_1_free") && checkProductName(cart.getStorageMap(), lineArray[2]))
                    cart.applyDiscount(new Discount_BUY_3_GET_1_FREE(), lineArray[2]);
                else if (lineArray[1].equals("buy_1_get_30_percentage") &&
                        checkProductName(cart.getStorageMap(), lineArray[2]))
                    cart.applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), lineArray[2]);
                else System.out.println("You entered the wrong type of discount. Try next command, for example," +
                            "\"discount buy_3_get_1_free soap\"");
            }
            default -> System.out.println("неизвесная команда, попробуйте еще раз, например \"add bear 5\"");
        }
    }

    private static boolean checkProductName(Map<String, Product> cart, String productName) {
        return cart.containsKey(productName);
    }

    private void showTooltipWithCommands() {
        System.out.println("\"price\" - вывести сумму товара");
        System.out.println("\"add bear 5\" - добавить товар в корзину. Структура: add [название продукта] [кол-во продукта]");
        System.out.println("\"discount buy_1_get_30_percentage cola\" - применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.println("\"discount buy_3_get_1_free bear\" - применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.println("\"finish\" - завершить работу");
    }
}
