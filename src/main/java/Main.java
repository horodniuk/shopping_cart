import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
import java.util.Scanner;


public class Main {

    public static final String SRC_MAIN_RESOURCES = "src/main/resources/";

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
            System.out.println("File mode режим - enter \"shopping_products_storage.json commadsList.txt\"");

            String line = new Scanner(System.in).nextLine();

            if (line.equals("finish")) return;

            String[] strArray = line.split(" ");
            String pathToStorageProduct = SRC_MAIN_RESOURCES + strArray[0];
            String pathToCommandList = SRC_MAIN_RESOURCES + strArray[strArray.length - 1];
            // checking if json file exists
            if (!isFileJsExists(pathToStorageProduct)) {
                throw new RuntimeException("File " + line + " not found!");
            } else if (line.equals("shopping_products_storage.json")) {
                new InteractiveModeRunner(SRC_MAIN_RESOURCES + line).start();
            }
            // checking if file with commands exist
            else if (new File(pathToCommandList).exists() &&
                    line.contains("shopping_products_storage.json commadsList.txt")) {
                new FileModeRunner(pathToStorageProduct, pathToCommandList).start();
            } else {
                System.out.println("You entered incorrect data");
            }
        }
    }

    private static boolean isFileJsExists(String pathToStorageProduct) {
        return new File(pathToStorageProduct).exists();
    }
}



