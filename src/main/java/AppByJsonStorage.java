
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import runner.FileModeRunner;
import runner.InteractiveModeRunner;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Slf4j
public class AppByJsonStorage {
    /*
     * Method in endless cycle is asking user to enter file name.
     *
     * If user entered in command line only correct name of json file -
     *  then starts new InteractiveModeRunner#start
     *
     * If user entered in command line json file name and through space name of .txt file with commands -
     *   then starts new FileModeRunner#start
     */
    static void start() {
        log.info("JSon storage start.");
        printPreviewToConsole();
        String line = getLineToConsole();
        String[] strArray = line.split(" ");
        isPathCorrect(strArray[0], strArray);
        if (strArray.length == 2) {
            log.info("User choose interactive mode runner.");
            String pathToStorageProduct = strArray[0] + "/" + strArray[1];
            File storageProduct = getAccessToFileByCopy(pathToStorageProduct);
            Storage storage = new StorageWithJson(storageProduct);
            new InteractiveModeRunner(storage).start();
        } else if (strArray.length == 3) {
            log.info("User choose file mode runner.");
            String pathToStorageProduct = strArray[0] + "/" + strArray[1];
            String pathToCommandList = strArray[0] + "/" + strArray[2];   // File separator
            File storageProduct = getAccessToFileByCopy(pathToStorageProduct);
            Storage storage = new StorageWithJson(storageProduct);
            File commandList = getAccessToFileByCopy(pathToCommandList);
            new FileModeRunner(storage, commandList).start();
        } else {
            log.info("User entered an invalid command.");
            System.out.println("incorrectly command");
        }
    }

    /*
     * if we run the program from the console by jar file, we need to copy the resource files
     * and get access to file
     */
    private static File getAccessToFileByCopy(String path) {
        InputStream in = AppByDataBaseStorage.class.getClassLoader().getResourceAsStream(path);
        try {
            File output = new File(Paths.get("temp/temp_") + FilenameUtils.getName(path));
            FileUtils.copyInputStreamToFile(in, output);
            in.close();
            log.info("The user add and uses: "+output.getName());
            return output;
        } catch (IOException e) {
            log.info("User specified an incorrect path to the file.");
            throw new IllegalArgumentException("Incorrect path to file " + path);
        }
    }

    /*
     * check for the existence of a file and folders
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
            log.info("Resource " + path + " not found!");
            throw new IllegalArgumentException("Resource " + path + " not found!");
        }
        return Path.of(path);
    }

    private static String getLineToConsole() {
        return new Scanner(System.in).nextLine();
    }

    private static void printPreviewToConsole() {
        System.out.println("Choose mode:");
        System.out.println("Interactive mode - enter \"market storage.json\"");
        System.out.println("File mode режим - enter \"market storage.json commadsList.txt\"");
    }
}









