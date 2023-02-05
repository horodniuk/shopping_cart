import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import runner.FileModeRunner;
import runner.InteractiveModeRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        new ConfigManager().start();
    }
}









