import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
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
        while (true) {
            System.out.println("Choose mode:");
            System.out.println("Interactive mode - enter \"shopping_products_storage.json\"");
            System.out.println("File mode режим - введите \"shopping_products_storage.json commadsList.txt\"");

            String line = new Scanner(System.in).nextLine();
            // checking if json file exists
            String absolutePath="src/main/resources/";//CORRECTION №1(Korbut)
            if (line.equals("shopping_products_storage.json") && new File(absolutePath + line).exists()) {
                new InteractiveModeRunner(absolutePath + line).start();
                break;
            }
            // checking if json file and file with commands exist
            String[] strArray = line.split(" ");
            String pathToStorageProduct = absolutePath + strArray[0];
            String pathToCommandList = absolutePath + strArray[strArray.length - 1];
            if (new File(pathToStorageProduct).exists() && new File(pathToCommandList).exists() &&
                    line.contains("shopping_products_storage.json commadsList.txt")) {
                new FileModeRunner(pathToStorageProduct, pathToCommandList).start();
                break;
            }
            System.out.println("You entered incorrect data");
        }
    }
}



