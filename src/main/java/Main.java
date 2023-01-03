import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
import java.util.Scanner;


public class Main {
    /**
     * запускаем программу, пользователь должен выбрать режим игры (Interactive mode или File mode)
     */
    public static void main(String[] args) {
        start();
    }

    /*
     * Метод в бесконечном цикле запрашивает название файла у пользователя.
     *
     * Если пользователь ввел в коммандной строке только корректное название файла .json -
     *  запускается new InteractiveModeRunner#start
     *
     * Если пользователь ввел в коммандной строке назване файла json и через пробел навание файла .txt с командами -
     *   запускается new FileModeRunner#start
     */
    private static void start() {
        while (true) {
            System.out.println("Выберите режим:");
            System.out.println("Interactive mode режим - введите \"shopping_products_storage.json\"");
            System.out.println("File mode режим - введите \"shopping_products_storage.json commadsList.txt\"");

            String line = new Scanner(System.in).nextLine();
            // проверка на существование файла json
            if (line.equals("shopping_products_storage.json") && new File("src/main/resources/" + line).exists()) {
                new InteractiveModeRunner("src/main/resources/" + line).start();
                break;
            }
            // проверка на существование файла json и файла с командами
            String[] strArray = line.split(" ");
            String pathToStorageProduct = "src/main/resources/" + strArray[0];
            String pathToCommandList = "src/main/resources/" + strArray[strArray.length - 1];
            if (new File(pathToStorageProduct).exists() && new File(pathToCommandList).exists() &&
                line.contains("shopping_products_storage.json commadsList.txt")) {
                new FileModeRunner(pathToStorageProduct, pathToCommandList).start();
                break;
            }
            System.out.println("Вы ввели некоректные данные");
        }
    }
}



