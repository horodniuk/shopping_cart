package commands;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class CommandFinish extends Command {

    //Example: finish
    private final Pattern regex = (Pattern.compile("(^finish$)"));

    //method which finds out if Pattern matches string
    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
