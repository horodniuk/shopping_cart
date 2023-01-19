package commands;

import java.util.regex.Pattern;

public abstract class Command {

    private Pattern regex;

    //method which finds out if Pattern matches string
    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public Pattern getRegex() {
        return regex;
    }
}
