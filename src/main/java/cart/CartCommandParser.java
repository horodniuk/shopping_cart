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

public class CartCommandParser {
    private static List<String> discounts;
    private static List<String> products;
    private static final AddCommand addCommand = new AddCommand();
    private static final DiscountCommand discountCommand = new DiscountCommand();
    private static final FinishCommand finishCommand = new FinishCommand();
    private static final PriceCommand priceCommand = new PriceCommand();
    private static final RemoveCommand removeCommand = new RemoveCommand();

    private static Map<Command, Pattern> commands = new HashMap<>() {{
        commands.put(addCommand, addCommand.getRegex());
        commands.put(discountCommand, discountCommand.getRegex());
        commands.put(finishCommand, finishCommand.getRegex());
        commands.put(priceCommand, priceCommand.getRegex());
        commands.put(removeCommand, removeCommand.getRegex());
    }};

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
    public Optional<Command> parse(String line) {
        Optional<Command> parsedCommandOptional = Optional.empty();
        for (Command currentCommand : commands.keySet()) {
            if (currentCommand.matches(line)) {
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

    public static List<String> getDiscounts() {
        return discounts;
    }

    public static List<String> getProducts() {
        return products;
    }
}
