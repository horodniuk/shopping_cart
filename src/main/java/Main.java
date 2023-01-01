import cart.Cart;
import discount.Discount_BUY_1_GET_30_PERCENT_OFF;
import discount.Discount_BUY_3_GET_1_FREE;
import runner.FileModeRunner;
import runner.InteractiveModeRunner;
import storage.StorageWithJson;

import java.io.File;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    /**
     * запускаем программу, пользователь доллжен ввести режим игры (Interactive mode или File mode)
     */
    public static void main(String[] args) {

        start();

    /*  // тест Discount_BUY_3_GET_1_FREE
    Cart cart = new Cart(new StorageWithJson("/"));
    cart.add("bear", 3);
    cart.applyDiscount(new Discount_BUY_3_GET_1_FREE(), "bear");
    cart.price();*/

    /* // тест Discount_BUY_3_GET_1_FREE
    Cart cart = new Cart(new StorageWithJson("/"));
    cart.add("bear", 3);
    cart.add("soap", 1);
    cart.applyDiscount(new Discount_BUY_1_GET_30_PERCENT_OFF(), "soap");
    cart.price();*/
    }

    /**
     * Задача: переписать метод с проверками и написать к нему тесты
     * Сейчас метод в бесконечном цикле запрашивает данные у пользователя
     * Если пользователь ввел в коммандной строке корректное назване файла json -
     *  запускается new InteractiveModeRunner#start
     *
     * Если пользователь ввел в коммандной строке назване файла json и через пробел навание файла с командами -
     *   запускается new FileModeRunner#start
     *
     *  Сейчас что бы работало я разделил через пробел и сравнил существуют ли пути + соеденяю из строкой "src/main/resources/"
     */
    private static void start() {
        while (true) {
            System.out.println("Выберите режим:");
            System.out.println("Interactive mode режим - введите \"shopping_products_storage.json\"");
            System.out.println("File mode режим - введите \"shopping_products_storage.json commadsList.txt\"");
            String str = new Scanner(System.in).nextLine();
            // проверка на существование файла json
            if (new File("src/main/resources/" + str).exists() && !Pattern.matches("[\s]", str)) {
                new InteractiveModeRunner("src/main/resources/" + str).start();
                return;
            }
            // проверка на существование файла json и файла с командами
            String[] strArray = str.split(" ");
            String storagePath = "src/main/resources/" + strArray[0];
            String commandLinePath = "src/main/resources/" + strArray[strArray.length - 1];
            if (new File(storagePath).exists() && new File(commandLinePath).exists()) {
                new FileModeRunner(storagePath, commandLinePath).start();
                return;
            }
            System.out.println("Вы ввели некоректные данные");
        }
    }
}



