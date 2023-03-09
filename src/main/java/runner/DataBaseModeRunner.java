package runner;

import cart.Cart;
import config.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import storage.Storage;
import storage.StorageDataBase;
import storage.StorageDataBaseByHibernate;
import storage.StorageDataBaseByJDBC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class DataBaseModeRunner implements ModeRunner {
    private final List<StorageDataBase> dataBaseList =
            List.of(new StorageDataBaseByHibernate(), new StorageDataBaseByJDBC());

    /**
     * Method description
     * starts reading and executing commands line by line from console;
     * first we output message to console with method - showTooltipWithCommands().
     * next we get property (db.connection_type) and assign it to variable connectionType;
     * after that we check if any of storageDataBase has the same connection type as specified in properties;
     * if true - we start method load in this storage and pass on this storage to Cart, if false - we go further;
     * after that we check - if cart equals null;
     * if true - we throw RuntimeException, if false we go further;
     * next we create instance of Class TextCommandExecutor
     * after that we create instance of class Cart;
     * next we start endless cycle in which we receive read commands from console;
     * each line we pass to method executeCommand() to execute each command;
     * if line equals "finish" - we end this cycle while().
     * method executeCommand() - executes command in line from file;
     * method showTooltipWithCommands() - outputs message to console with tips.
     */
    @Override
    public void start() {
        System.out.println("Starting DataBase mode.");
        ModeRunner.showTooltipWithCommands();
        String connectionType = PropertyUtils.get("db.connection_type");
        Cart cart = new Cart(parseStorage(connectionType));
        TextCommandExecutor textCommandExecutor = new TextCommandExecutor();
        while (true) {
            String line = new Scanner(System.in).nextLine();
            textCommandExecutor.executeCommand(line, cart);
            if (line.equals("finish")) return;
        }
    }

    private Storage parseStorage(String connectionType) {
        Map<String, StorageDataBase> dataBaseMap = new HashMap<>();
        for (StorageDataBase currentStorage : dataBaseList) {
            dataBaseMap.put(currentStorage.getConnectionType(), currentStorage);
        }
        var currentStorage = dataBaseMap.get(connectionType);
        if (currentStorage != null) {
            currentStorage.load();
            return currentStorage;
        } else {
            log.error("Wrong db.connection_type specified in config file!");
            throw new RuntimeException("Wrong db.connection_type specified in config file!");
        }
    }
}
