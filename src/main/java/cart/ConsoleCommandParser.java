package cart;

import commands.*;
import discount.Discount;
import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleCommandParser {
    private List<Discount> discounts; // List of all discounts
    private List<String> products; // List of all product names stored in Storage
    private Map<Command, Pattern> commandsMap; // Map of all commands (keys) and Patterns for these commands (values)


    public ConsoleCommandParser(Cart cart) {
        this.products = cart.getStorage().getProductNames(); // getting all names of products from storage
        this.discounts = fillDiscountList(); //discounts commands;
        this.commandsMap = fillCommandsMap(); // filling Map of commands and patterns with method fillCommandsMap()
    }

    /**
     * Method description
     * Method parameters -
     * Initializing Map with Commands (keys) and Patterns for this commands (values).
     * After that we initialize patterns for each command (add, discount, remove, price and finish).
     * after that we put commands and patterns in map
     * return - we return map of Commands and Patterns.
     * Example of patterns:
     * addPattern - add bear 5 or add cola 1 or add soap 2;
     * discountPattern - discount buy_1_get_30_percentage cola or discount buy_3_get_1_free bear;
     * removePattern - remove bear 5 or remove cola 1, remove soap 2;
     * finishPattern - finish;
     * pricePattern - price.
     * return - returns Map of Commands (keys) and patterns (values).
     */
    private Map<Command, Pattern> fillCommandsMap() {
        Map<Command, Pattern> commands = new HashMap<>();
        // Example: add bear 5, add cola 1, add soap 2
        final Pattern addPattern = (Pattern.compile("^(add) (" + createRegExValues(products) + ") " +
                "([0-9]+)"));
        //Example: discount buy_1_get_30_percentage cola,  discount buy_3_get_1_free bear
        final Pattern discountPattern = (Pattern.compile("^(discount) (" +
                createRegExValuesDiscounts(discounts) + ") (" + createRegExValues(products) + ")"));
        // Example: remove bear 5, remove cola 1, remove soap 2
        final Pattern removePattern = (Pattern.compile("^(remove) (" + createRegExValues(products) + ")" +
                " ([0-9]+)"));
        // example: finish
        final Pattern finishPattern = (Pattern.compile("(^finish$)"));
        // example: price
        final Pattern pricePattern = (Pattern.compile("(^price$)"));

        commands.put(new AddCommand(), addPattern);
        commands.put(new DiscountCommand(), discountPattern);
        commands.put(new FinishCommand(), finishPattern);
        commands.put(new PriceCommand(), pricePattern);
        commands.put(new RemoveCommand(), removePattern);
        return commands;
    }

    /**
     * Method description
     * Method for filling List of discounts which is a parameter of this class.
     * return - list of discounts.
     */
    private List<Discount> fillDiscountList() {
        return List.of(new Discount_buy_1_get_30_percent_off(), new Discount_buy_3_get_1_free());
    }

    /**
     * Method description
     * Method parameters - string, which we get after reading file or reading command console
     * Initializing Optional of class Command.
     * After that in cycle for each we check if commands (keys from map commandsMap) match
     * after that with the help of cycle foreach we check if any commands Pattern matches string line,
     * if we find this command - then we get arguments with the help of method getArgumentsWithMatcher() and passing
     * parameters to each command with method receiveArguments().
     * Then return Optional of class Command.
     * If command is not found - we return empty optional of class Command and throw IllegalArgumentException.
     * return - we return Optional of class Command.
     */
    public Command parse(String line) {
        Optional<Command> parsedCommandOptional = Optional.empty();
        for (Command currentCommand : commandsMap.keySet()) {
            if (matches(line, currentCommand)) {
                final Matcher matcher = commandsMap.get(currentCommand).matcher(line);
                currentCommand.receiveArguments(getArgumentsWithMatcher(matcher));
                parsedCommandOptional = Optional.of(currentCommand);
            }
        }
        if (parsedCommandOptional.isEmpty())
            throw new IllegalArgumentException("Unknown command, try again, for example \"add beer 5\"");
        else {
            return parsedCommandOptional.get();
        }
    }

    /**
     * Method description
     * Method parameters - instance of class Matcher
     * we create an empty ArrayList
     * then we check if String line matches Pattern
     * Next we create int countGroup which stores the numbers of groups in Pattern
     * and collect in Arraylist using stream all the arguments by number of groups.
     * return - we return list of arguments which correspond to pattern groups
     */
    private List<String> getArgumentsWithMatcher(Matcher matcher) {
        List<String> list = new ArrayList<>();
        if (matcher.find()) {
            int countGroup = matcher.groupCount();
            list = IntStream.rangeClosed(1, countGroup)
                    .mapToObj(matcher::group)
                    .collect(Collectors.toList());
        }
        return list;
    }

    /**
     * Method description
     * Method parameters - string name of command
     * depending on discount name we create discount object
     * buy_1_get_30_percentage -> Discount_BUY_1_GET_30_PERCENT_OFF();
     * buy_3_get_1_free -> Discount_BUY_3_GET_1_FREE();
     * return - we return instance of discount.
     */
    public static Discount parseDiscount(String nameCommand) {
        if (nameCommand.equals("buy_1_get_30_percentage")) {
            return new Discount_buy_1_get_30_percent_off();
        } else {
            return new Discount_buy_3_get_1_free();
        }
    }

    /**
     * Method description
     * Method parameters - list of string name of products
     * from list converting to String, to apply in regular expression
     * For example: List.of("beer", "soap", "cola") ->
     * "beer|soap|cola"
     * return - string of all product names with delimiter |.
     */
    private String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    /**
     * Method description
     * Method parameters - list of instances of discounts
     * from list instances of discounts we get the names of this commands and add them to new List of discount names,
     * to apply it in regular expression
     * For example: from List<Discount> discounts we get names of each discount, add them to list of discount names and
     * then make string from them -> "buy_1_get_30_percentage|buy_3_get_1_free"
     * return - string of all discount names with delimiter |.
     */
    private String createRegExValuesDiscounts(List<Discount> values) {
        List<String> discountNames = new ArrayList<>();
        for (Discount discount : values) {
            discountNames.add(discount.getDiscountName());
        }
        return String.join("|", discountNames);
    }

    /**
     * Method description
     * Method parameters - string which we get from text file or console and command
     * we check if Pattern (which is value found by key - command) matches the string.
     * return - we return true if matches and false if not
     */
    public Boolean matches(String text, Command command) {
        return commandsMap.get(command).matcher(text).find();
    }
}
