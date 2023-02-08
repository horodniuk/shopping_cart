package runner;

import cart.Cart;
import storage.StorageDataBase;

import java.util.Scanner;

public class DataBaseModeRunner implements ModeRunner {
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
    @Override
    public void start() {
        System.out.println("Starting DataBase mode.");
        showTooltipWithCommands();
        System.out.println("Enter the command in console:");
        Cart cart = new Cart(new StorageDataBase());
        TextCommandExecutor textCommandExecutor = new TextCommandExecutor();
        while (true) {
            String line = new Scanner(System.in).nextLine();
            textCommandExecutor.executeCommand(line, cart);
            if (line.equals("finish")) return;
        }

    }

    /**
     * Show instruction to Customer
     */
    private void showTooltipWithCommands() {
        System.out.println("\n---------------------------------INSTRUCTION-------------------------------------------" +
                           "-----------------------------------------");
        System.out.printf("\n%-40s  %-30s  %-10s -> %-30s ", "\"add beer 5\"", "- add item to cart.", "Structure:",
                "add [product name] [product quantity]");
        System.out.printf("\n%-40s  %-30s  %-10s -> %-30s ", "\"remove beer 5\"", "- remove item from cart.",
                "Structure:", "remove [product name] [product quantity]");
        System.out.printf("\n%-40s  %-30s  %-10s -> %-30s ", "\"discount buy_1_get_30_percentage beer\"",
                "- apply discount.", "Structure:", "discount [discount name] [product name]");
        System.out.printf("\n%-40s  %-30s  %-10s -> %-30s ", "\"discount buy_3_get_1_free cola\"",
                "- apply discount.", "Structure:", "discount [discount name] [product name]");
        System.out.printf("\n%-40s  %-30s ", "\"price\"", "- find out the price.");
        System.out.printf("\n%-40s  %-30s ", "\"finish\"", "- grocery shopping completed.");
        System.out.println("");
        System.out.println("\n---------------------------------------------------------------------------------------" +
                           "-----------------------------------------");
    }
}
