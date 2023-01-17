import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {


    /**
     * After start of the program, user must choose mode (Interactive mode or File mode)
     */
    public static void main(String[] args) {
        start();
    }


    /*
     * Method in endless cycle is asking user to enter file name.
     *
     * If user entered in command line only correct name of json file -
     *  then starts new InteractiveModeRunner#start
     *
     * If user entered in command line json file name and through space name of .txt file with commands -
     *   then starts new FileModeRunner#start
     */
    private static void start() {
        final String RESOURCES_PATH = "src/main/resources/";

        System.out.println("Choose mode:");
        System.out.println("Interactive mode - enter \"./market storage.json\"");
        System.out.println("File mode режим - enter \"./market storage.json commadsList.txt\"");

        String line = new Scanner(System.in).nextLine();
        String[] strArray = line.split(" ");

        if (
                (strArray.length == 2) &&   // проверка что только два элемента (папка и файл)
                (Files.isDirectory(Path.of(RESOURCES_PATH + strArray[0]))) && // проверка что первый элемент - папка существует
                (Files.exists(Path.of(RESOURCES_PATH + strArray[0] + "/" + strArray[1]))) // проверка что второй элемент - файл существует
        ) {
            String pathToStorageProduct = RESOURCES_PATH + strArray[0] + "/" + strArray[1];
            new InteractiveModeRunner(pathToStorageProduct).start();
        } else {
            if (
                    (strArray.length == 3) && // проверка что три элемента (папка, файл, файл)
                    (Files.isDirectory(Path.of(RESOURCES_PATH + strArray[0]))) && // проверка что первый элемент - папка существует
                    (Files.exists(Path.of(RESOURCES_PATH + strArray[0] + "/" + strArray[1]))) && // проверка что второй элемент - файл существует
                    (Files.exists(Path.of(RESOURCES_PATH + strArray[0] + "/" + strArray[2]))) // проверка что третий элемент - файл существует
            ) {
                String pathToStorageProduct = RESOURCES_PATH + strArray[0] + "/" + strArray[1];
                String pathToCommandList = RESOURCES_PATH + strArray[0] + "/" + strArray[2];
                new FileModeRunner(pathToStorageProduct, pathToCommandList).start();
            } else {
                System.out.println("incorrectly command");
            }
        }
    }
}









