package seedu.duke.parser;

import seedu.duke.command.Command;

public class Parser {
    public Command parse(String userInput) {
        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String arguments = parts.length >1 ? parts[1]: "";
        return new Command(commandType, arguments);
    }


}
