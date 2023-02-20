package runner;

import cart.Cart;
import lombok.extern.slf4j.Slf4j;
import storage.Storage;

import java.util.Scanner;

/**
 * Reading commands line by line from console and if such commands exist -
 * perform them. (look method executeCommand())
 */
@Slf4j
public class InteractiveModeRunner implements ModeRunner {
    private Storage storage;

    public InteractiveModeRunner(Storage storage) {
        this.storage = storage;
    }

    /**
     * Method description
     * starts reading and executing commands line by line from console;
     * first we output message to console with method - showTooltipWithCommands().
     * next we create instance of Class TextCommandExecutor
     * after that we create instance of class Cart;
     * next we start endless cycle in which we receive read commands from console;
     * each line we pass to method executeCommand() to execute each command;
     * if line equals "finish" - we end this cycle while().
     * method executeCommand() - executes command in line from file;
     * method showTooltipWithCommands() - outputs message to console with tips.
     */
    public void start() {
        log.info("Running method start.");
        System.out.println("Starting Interactive mode.");
        ModeRunner.showTooltipWithCommands();
        System.out.println("Enter the command in console:");
        Cart cart = new Cart(storage);
        TextCommandExecutor textCommandExecutor = new TextCommandExecutor();
        while (true) {
            String line = new Scanner(System.in).nextLine();
            log.info("User enter "+line+" command.");
            textCommandExecutor.executeCommand(line, cart);
            if (line.equals("finish")){
                log.info("User enter finish command.");
                return;
            }
        }
    }


}
