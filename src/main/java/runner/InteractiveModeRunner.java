package runner;

import cart.Cart;
import cart.CartCommandParser;
import cart.Product;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;
import storage.StorageWithJson;

import java.util.Map;
import java.util.Scanner;

/**
 * Считываем построчно команды из консоли и если такие команды существуют -
 * выполняем их. (смотреть метод excecuteCommand())
 */
public class InteractiveModeRunner implements ModeRunner {
    private String pathToStorage;

    public InteractiveModeRunner(String pathToStorage) {
        this.pathToStorage = pathToStorage;
    }

    /**
     * Считываем построчно команды из консоли.
     */
    public void start() {
        System.out.println("Запускаем режим Interactive mode.");
        showTooltipWithCommands();
        System.out.println("Введите команду в консоль:");
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        while (true) {
            String line = new Scanner(System.in).nextLine();
            if(line.equals("finish")) return;
            excecuteCommand(line, cart);
        }
    }

    /*
     * выполнено
     * Задача
     * Нужно прописать метод который будет выполнять команды, которые указаны в тех.задании
     * Например: Если на вход подается строка "add cola 5" нужно распарсить для получения
     * названия продукта "cola" и кол-ва "5" и проверить есть ли такой продукт,
     * есть ли его достаточное кол-во и после выполнить команду.
     * например:
     * add cola 5 --> cart.add("cola", 5)
     * add soap 2 --> cart.add("soap", 2)
     * discount buy_1_get_30_percentage cola --> applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), "cola")
     * discount buy_3_get_1_free soap --> applyDiscount(new Discount_BUY_3_GET_1_FREE(), "soap")
     */
    @Override
    public void excecuteCommand(String line, Cart cart) {
        CartCommandParser cartCommandParser = new CartCommandParser(cart);
        if (cartCommandParser.parse(line)) return;
        if (line.equals("price")) {
            cart.price();
        } else{
            System.out.println("неизвесная команда, попробуйте еще раз, например \"add cola 5\"");
        }
    }

    /**
     * Показать пользователю инструкцию
     */
    private void showTooltipWithCommands() {
        System.out.println("Инструкция:");
        System.out.println("\"price\" - вывести сумму товара");
        System.out.println("\"add cola 5\" - добавить товар в корзину. Структура: add [название продукта] [кол-во продукта]");
        System.out.println("\"discount buy_1_get_30_percentage cola\" - применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.println("\"discount buy_3_get_1_free bear\" - применить скидку. Структура: discount [название скидки] [название продукта]");
        System.out.println("\"finish\" - завершить работу");
    }
}
