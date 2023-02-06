
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import storage.Storage;
import storage.StorageWithJson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigLoader {

    public Storage load(URI path) {
        Storage storage = null;
        try (FileReader fileReader = new FileReader(path.getPath())) {
            Properties property = new Properties();
            property.load(fileReader);
            String dbType = property.getProperty("DB-TYPE");
            if (dbType.equals("STORAGE_JSON")) {
                File dbPath = getAccessToFileByCopy(property.getProperty("DB-PATH"));
                storage = new StorageWithJson(dbPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return storage;
    }

    /*
     * if we run the program from the console by jar file, we need to copy the resource files
     * and get access to file
     */
    private File getAccessToFileByCopy(String path) {
        InputStream in = Main.class.getClassLoader().getResourceAsStream(path);
        try {
            File output = new File(Paths.get("temp/temp_") + FilenameUtils.getName(path));
            FileUtils.copyInputStreamToFile(in, output);
            in.close();
            return output;
        } catch (IOException e) {
            throw new IllegalArgumentException("Incorrect path to file " + path);
        }
    }

    /*
     * check for the existence of a file and folders
     */
    private boolean isPathCorrect(String folder, String... files) {
        isDirectoryPathExist(folder);
        for (int i = 1; i < files.length; i++) {
            isFilePathExist(folder + "/" + files[i]);
        }
        return true;
    }

    private boolean isFilePathExist(String path) {
        return Files.exists(getPath(path));
    }

    private boolean isDirectoryPathExist(String path) {
        return Files.isDirectory(getPath(path));
    }

    private Path getPath(String path) {
        final URL url = Main.class.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Resource " + path + " not found!");
        }
        return Path.of(path);
    }

}
