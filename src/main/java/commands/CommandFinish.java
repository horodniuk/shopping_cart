package commands;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class CommandFinish extends Command {

    //Example: finish
    private final Pattern regex = (Pattern.compile("(^finish$)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
