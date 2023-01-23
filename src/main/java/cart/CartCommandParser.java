package cart;

import commands.*;
import discount.Discount;
import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;
import storage.Storage;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CartCommandParser {
    private final List<String> discounts;
    private final List<String> products;
    private final Map<Command, Pattern> commands;

    Map<Command, Pattern> createComands() {
        Map<Command, Pattern> commands = new HashMap<>();
        final Pattern regexAdd = Pattern.compile("^(add) " +
                                                 "(" + createRegExValues(getProducts()) + ")" +
                                                 " ([0-9]+)");

        final Pattern regexDiscount = Pattern.compile("^(discount)" +
                                                      " (" + createRegExValues(getDiscounts()) + ")" +
                                                      " (" + createRegExValues(getProducts()) + ")");

        final Pattern regexRemove = Pattern.compile("^(remove) " +
                                                    "(" + createRegExValues(getProducts()) + ")" +
                                                    "([0-9]+)");

        final Pattern regexFinish = Pattern.compile("(^finish$)");
        final Pattern regexPrice = Pattern.compile("(^price$)");

        commands.put(new CommandAdd(), regexAdd);
        commands.put(new CommandDiscount(), regexDiscount);
        commands.put(new CommandRemove(), regexRemove);
        commands.put(new CommandFinish(), regexFinish);
        commands.put(new CommandPrice(), regexPrice);
        return commands;
    }

    public CartCommandParser(Cart cart) {
        discounts = List.of("buy_1_get_30_percentage", "buy_3_get_1_free");
        products = cart.getStorage().getProductNames(); // getting all names of products from storage
        commands = createComands();
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
        for (Map.Entry<Command, Pattern> currentCommand : commands.entrySet()) {
            if (currentCommand.getValue().matcher(line).find()) {
                final Matcher matcher = currentCommand.getValue().matcher(line);
                List<String> arguments = getArgumentsWithMatcher(matcher);
                Command command = currentCommand.getKey();
                command.setArguments(arguments);
                return Optional.of(currentCommand.getKey());
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
            list = IntStream.rangeClosed(1, countGroup)
                    .mapToObj(matcher::group)
                    .collect(Collectors.toList());
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
    public String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public List<String> getProducts() {
        return products;
    }
}
