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
     * First we start the method printPreviewToConsole(), which outputs message to user in console;
     * next create string line with method getLineToConsole();
     * after that we create array of strings (strArray) from line, splitting it in strings by regular expression " ";
     * then we start method isPathCorrect() which checks if path that user provided is correct;
     * next we check if strArray length equals 2, if true:
     * we create string pathToStorageProduct which consists of zero element of array ("./market") and first element of
     * array ("storage.json") separated by File.separator;
     * and create new instance of InteractiveModeRunner class, and start this mode with method start();
     * if false: we check next statement;
     * next we check if strArray length equals 3, if true:
     * we create String pathToStorageProduct which consists of zero element of array ("./market") and first element of
     * array ("storage.json") separated by string - / ;
     * next we create String pathToCommandList which consists of zero element of array ("./market") and second element
     * of array ("commadsList.txt") separated by string - / ;
     * after that we create instance of FileModeRunner class, and start this mode with method start();
     * if false: we output message to console, that user entered incorrect command;*
     * method printPreviewToConsole() - outputs message to user in console;
     * method getLineToConsole() - gets string line from console, typed by user;
     * method isPathCorrect() - checks if path that user provided is correct;
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
     * parameters - string folder and string files
     * checks if path that user provided is correct;
     * first we check if such directory exists with method isDirectoryPathExist();
     * next we check if file exists with method isFilePathExist();
     * method isDirectoryPathExist() - checks if directory exists
     * method isFilePathExist() - checks if file exists
     */
    private static boolean isPathCorrect(String folder, String... files) {
        isDirectoryPathExist(folder);
        for (int i = 1; i < files.length; i++) {
            isFilePathExist(folder + "/" + files[i]);
        }
        return true;
    }

    /**
     * Method description
     * parameters - string path;
     * checks if file exists;
     * returns true if exists;
     * returns false if not.
     */
    private static boolean isFilePathExist(String path) {
        return Files.exists(getPath(path));
    }

    /**
     * Method description
     * parameters - string path;
     * checks if directory exists;
     * returns true if exists;
     * returns false if not.
     */
    private static boolean isDirectoryPathExist(String path) {
        return Files.isDirectory(getPath(path));
    }

    /**
     * Method description
     * parameters - string path;
     * first we create instance of class URL which contains path to resources;
     * next we check if url == null, if true - we throw IllegalArgumentException;
     * next in try catch we return instance of class Path, which can throw URISyntaxException, which we catch and
     * if we get it - then throw InvalidPathException;
     * returns instance of class Path.
     */
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

    /**
     * Method description
     * creates string line after reading it from console;
     * returns string line.
     */
    private static String getLineToConsole() {
        return new Scanner(System.in).nextLine();
    }

    /**
     * Method description
     * outputs message to user in console, that user should choose mode;
     */
    private static void printPreviewToConsole() {
        System.out.println("Choose mode:");
        System.out.println("Interactive mode - enter \"./market storage.json\"");
        System.out.println("File mode режим - enter \"./market storage.json commadsList.txt\"");
    }
}









