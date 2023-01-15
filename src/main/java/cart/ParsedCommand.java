package cart;

import commands.Command;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParsedCommand {

    private Command command;
    private List<String> arguments;
}

