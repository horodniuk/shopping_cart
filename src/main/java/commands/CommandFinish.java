package commands;

import lombok.Getter;

import java.util.regex.Pattern;

public class CommandFinish extends Command {
    @Getter
    private final Pattern regex = (Pattern.compile("(^finish$)"));
}
