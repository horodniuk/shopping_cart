import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {


    /**
     * After start of the program, user must choose mode (Interactive mode or File mode)
     */
    public static void main(String[] args) {
        start();
    }

    /**
     * Method description
     * Method in endless cycle is asking user to enter file name.
     * If user entered in command line only correct name of json file - then starts new InteractiveModeRunner#start
     * If user entered in command line json file name and through space name of .txt file with commands -
     * then starts new FileModeRunner#start
     */
    private static void start() {
        printPreviewToConsole();
        String line = getLineToConsole();
        String[] strArray = line.split(" ");
        isPathCorrect(strArray[0], strArray);
        if (strArray.length == 2) {
            String pathToStorageProduct = strArray[0] + File.separator + strArray[1];
            new InteractiveModeRunner(URI.create(String.valueOf(Main.class.getResource(pathToStorageProduct)))).start();
        } else if (strArray.length == 3) {
            String pathToStorageProduct = strArray[0] + "/" + strArray[1];
            String pathToCommandList = strArray[0] + "/" + strArray[2];
            new FileModeRunner(
                    URI.create(String.valueOf(Main.class.getResource(pathToStorageProduct))),
                    URI.create(String.valueOf(Main.class.getResource(pathToCommandList)))
            ).start();
        } else {
            System.out.println("incorrectly command");
        }
    }
    /**
     * Method description
     * Method in endless cycle is asking user to enter file name.
     * If user entered in command line only correct name of json file - then starts new InteractiveModeRunner#start
     * If user entered in command line json file name and through space name of .txt file with commands -
     * then starts new FileModeRunner#start
     */
    private static boolean isPathCorrect(String folder, String... files) {
        isDirectoryPathExist(folder);
        for (int i = 1; i < files.length; i++) {
            isFilePathExist(folder + "/" + files[i]);
        }
        return true;
    }

    private static boolean isFilePathExist(String path) {
        return Files.exists(getPath(path));
    }

    private static boolean isDirectoryPathExist(String path) {
        return Files.isDirectory(getPath(path));
    }

    private static Path getPath(String path) {
        final URL url = Main.class.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Resource " + path + " not found!");
        }
        try {
            return Path.of(Main.class.getResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new InvalidPathException(path, "cannot find path");
        }
    }

    private static String getLineToConsole() {
        return new Scanner(System.in).nextLine();
    }

    private static void printPreviewToConsole() {
        System.out.println("Choose mode:");
        System.out.println("Interactive mode - enter \"./market storage.json\"");
        System.out.println("File mode режим - enter \"./market storage.json commadsList.txt\"");
    }
}









