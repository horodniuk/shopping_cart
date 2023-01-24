package runner;

import cart.Cart;
import storage.StorageWithJson;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

/**
 * Reading commands line by line from console and if such commands exist -
 * perform them. (look method executeCommand())
 */
public class InteractiveModeRunner implements ModeRunner {
    private URI pathToStorage;

    public InteractiveModeRunner(URI pathToStorage) {
        this.pathToStorage = pathToStorage;
    }

    /**
     * Reading commands line by line from console.
     */
    public void start() {
        System.out.println("Starting Interactive mode.");
        showTooltipWithCommands();
        System.out.println("Enter the command in console:");
        Cart cart = null;
        try {
            cart = new Cart(new StorageWithJson(pathToStorage));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
