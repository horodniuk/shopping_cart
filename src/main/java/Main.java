import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {

    /*
    https://ru.stackoverflow.com/questions/799430/%D0%A1%D0%BC%D1%8B%D1%81%D0%BB-%D1%82%D0%BE%D1%87%D0%BA%D0%B0-%D1%81%D0%BB%D1%8D%D1%88-%D0%B2-%D0%BE%D0%B1%D0%BE%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B8-%D0%BF%D1%83%D1%82%D0%B8
    ./ указывает на то,что данный файл лежит в относительной директории,то есть,в той которая указана в контексте.
    Сейчас программа проверяет файл на существования а нам нужно проверить существует ли файл в указанной директории.

     */

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        final String RESOURCES_PATH = "src/main/resources/";
        // test data
        String variant1 = "./market storage.json";
        String variant2 = "./market storage.json commadsList.txt";

        String[] strArray = variant2.split(" "); // тестируем вариант 2
        if (
                (strArray.length == 2) &&   // проверка что только два элемента (папка и файл)
                (Files.isDirectory(Path.of(RESOURCES_PATH + strArray[0]))) && // проверка что первый элемент - папка существует
                (Files.exists(Path.of(RESOURCES_PATH + strArray[0] + "/" + strArray[1]))) // проверка что второй элемент - файл существует
        ) {
            System.out.println("File exists! - InteractiveModeRunner");
        } else {
            if (
                    (strArray.length == 3) && // проверка что три элемента (папка, файл, файл)
                    (Files.isDirectory(Path.of(RESOURCES_PATH + strArray[0]))) && // проверка что первый элемент - папка существует
                    (Files.exists(Path.of(RESOURCES_PATH + strArray[0] + "/" + strArray[1]))) && // проверка что второй элемент - файл существует
                    (Files.exists(Path.of(RESOURCES_PATH + strArray[0] + "/" + strArray[2]))) // проверка что третий элемент - файл существует
            ) {
                System.out.println("File exists! -  FileModeRunner");
            } else {
                System.out.println("File not exist");
            }
        }

    }
}



