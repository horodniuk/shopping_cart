import runner.FileModeRunner;
import runner.InteractiveModeRunner;
import runner.ModeRunner;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    /* Cart cart = new Cart(new StorageWithJson("shopping_products_storage.json"));
     System.out.println(cart.getCartMap());
     cart.add("bear", 5 );
     cart.add("soap", 5 );
     cart.add("cola", 11 );
        System.out.println(cart.getCartMap());
     cart.add("bear", 3 );
     cart.add("soap", 10 );
        System.out.println(cart.getCartMap());*/

        start();
    }

    // считываем строку если один параметр ->покзываем подсказку, если два -> читаем данные с файла
    private static void start() {
        while (true) {
            System.out.println("Выберите режим");
            System.out.println("Interactive mode режим - введите название файла с товарами(формат json). Пример-\"shopping_products_storage.json\"");
            System.out.println("File mode режим - введите название файла с товарами(формат json) и файл с командами через пробел. Пример-\"shopping_products_storage.json commadsList.txt\"");
            String str = new Scanner(System.in).nextLine();
            // если строка содержит корректный путь к файлу json проверить есть ли путь файлу с командами
            // нужно переписать нормальные проверки

            // проверка на файл json
            if (new File("src/main/resources/" + str).exists()) {
                new InteractiveModeRunner("src/main/resources/" + str).start();
                return;
            }
            String[] s = str.split(" ");
            String storagePath = "src/main/resources/" + s[0];
            String commandLinePath = "src/main/resources/" + s[s.length - 1];
            if (new File(storagePath).exists() && new File(commandLinePath).exists()) {
                new FileModeRunner(storagePath, commandLinePath).start();
                return;
            }
            System.out.println("Вы ввели некоректные данные");
        }
    }

}



