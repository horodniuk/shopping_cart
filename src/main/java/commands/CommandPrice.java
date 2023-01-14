package commands;

import lombok.Getter;

import java.util.regex.Pattern;

public class CommandPrice extends Command {

    @Getter
    private final Pattern regex = (Pattern.compile("(^price$)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
