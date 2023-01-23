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
    private static List<Discount> discounts = new ArrayList<>();
    private static List<String> products = new ArrayList<>();
    private static Command addCommand = new AddCommand();
    private static final Pattern addPattern = (Pattern.compile("^(add) (" + createRegExValues(products) + ") " +
            "([0-9]+)"));
    private static final Pattern discountPattern = (Pattern.compile("^(discount) (" +
            createRegExValuesDiscounts(discounts) + ") (" + createRegExValues(products) + ")"));
    private static final Pattern removePattern = (Pattern.compile("^(remove) (" + createRegExValues(products) + ")"
            + " ([0-9]+)"));
    private static final Pattern finishPattern = (Pattern.compile("(^finish$)"));
    private static final Pattern pricePattern = (Pattern.compile("(^price$)"));


    private static Map<Command, Pattern> commands = new HashMap<>();

    static {
        commands.put(addCommand, addPattern);
        commands.put(new DiscountCommand(), discountPattern);
        commands.put(new FinishCommand(), finishPattern);
        commands.put(new PriceCommand(), pricePattern);
        commands.put(new RemoveCommand(), removePattern);
    }

    public ConsoleCommandParser(Cart cart) {
        products = cart.getStorage().getProductNames(); // getting all names of products from storage
        discounts = List.of(new Discount_buy_1_get_30_percent_off(), new Discount_buy_3_get_1_free()); //discounts commands;
    }

    /**
     * Method description
     * Method parameters - string, which we get after reading file or reading command console
     * Initializing Optional of class ParsedCommand.
     * we create list of Commands,
     * after that with the help of cycle foreach we check if any command matches string line,
     * if we find this command - then we get arguments with the help of method getArgumentsWithMatcher()
     * and return Optional of class ParsedCommand which has parameters: found Command, and arguments.
     * return - we return Optional of class ParsedCommand.
     */
    public Optional<Command> parse(String line) {
        Optional<Command> parsedCommandOptional = Optional.empty();
        for (Command currentCommand : commands.keySet()) {
            if (matches(line, currentCommand)) {
                final Matcher matcher = commands.get(currentCommand).matcher(line);
                currentCommand.receiveArguments(getArgumentsWithMatcher(matcher));
                return Optional.of(currentCommand);
            }
        }
        return parsedCommandOptional;
    }

    /**
     * Method description
     * Method parameters - instance of class Matcher
     * we create an empty ArrayList
     * then we check if String line matches Pattern
     * Next we create int countGroup which stores the numbers if groups in Pattern
     * and collect in Arraylist using stream all the arguments by number of groups.
     * return - we return list of arguments which correspond to pattern groups
     */
    private List<String> getArgumentsWithMatcher(Matcher matcher) {
        List<String> list = new ArrayList<>();
        if (matcher.find()) {
            int countGroup = matcher.groupCount();
            list = IntStream.rangeClosed(1, countGroup).mapToObj(matcher::group).collect(Collectors.toList());
        }
        return list;
    }

    /*
     * depending on discount name we create discount object
     * buy_1_get_30_percentage -> Discount_BUY_1_GET_30_PERCENT_OFF();
     * buy_3_get_1_free -> Discount_BUY_3_GET_1_FREE();
     */
    public static Discount parseDiscount(String nameCommand) {
        if (nameCommand.equals("buy_1_get_30_percentage")) {
            return new Discount_buy_1_get_30_percent_off();
        } else {
            return new Discount_buy_3_get_1_free();
        }
    }

    /*
     * from list converting to String, to apply in regular expression
     * For example: List.of("buy_1_get_30_percentage", "buy_3_get_1_free") ->
     * "buy_1_get_30_percentage|buy_3_get_1_free"
     */
    private static String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    private static String createRegExValuesDiscounts(List<Discount> values) {
        List<String> discountNames = new ArrayList<>();
        for (Discount discount : values) {
            discountNames.add(discount.getClass().getSimpleName().replace("Discount_", ""));
        }
        return String.join("|", discountNames);
    }

    public Boolean matches(String text, Command command) {
        return commands.get(command).matcher(text).find();
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public List<String> getProducts() {
        return products;
    }
}
