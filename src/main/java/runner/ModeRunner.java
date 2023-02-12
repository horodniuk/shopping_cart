package runner;

/*
 * Interface for working with different modes of data output to the console
 * method start() - starts reading and executing commands depending on mode
 * For example:
 * if it is Interactive mode - we read the commands from console
 * if it is File mode - we read the commands from file
 *
 */
public interface ModeRunner {
    void start();

    /**
     * Show instruction to Customer
     */
   static void showTooltipWithCommands() {
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
