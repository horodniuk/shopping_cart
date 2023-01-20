package cart;

import commands.*;
import discount.Discount;
import discount.Discount_buy_1_get_30_percent_off;
import discount.Discount_buy_3_get_1_free;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CartCommandParser {
    private static List<String> discounts;
    private static List<String> products;

    public CartCommandParser(Cart cart) {
        products = cart.getStorage().getProductNames(); // getting all names of products from storage
        discounts = List.of("buy_1_get_30_percentage", "buy_3_get_1_free"); // discounts commands;
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
    public Optional<ParsedCommand> parse(String line) {
        Optional<ParsedCommand> parsedCommandOptional = Optional.empty();
        List<Command> commands = new ArrayList<>();
        commands.add(new CommandAdd());
        commands.add(new CommandDiscount());
        commands.add(new CommandFinish());
        commands.add(new CommandPrice());
        commands.add(new CommandRemoveSomeName());
        for (Command currentCommand : commands) {
            if (currentCommand.matches(line)) {
                final Matcher matcher = currentCommand.getRegex().matcher(line);
                List<String> arguments = getArgumentsWithMatcher(matcher);
                return Optional.of(new ParsedCommand(currentCommand, arguments));
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
    public Discount parseDiscount(String nameCommand) {
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
    public static String createRegExValues(List<String> values) {
        return String.join("|", values);
    }

    public static List<String> getDiscounts() {
        return discounts;
    }

    public static List<String> getProducts() {
        return products;
    }
}
