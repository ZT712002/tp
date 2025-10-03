package seedu.duke.parser;

import seedu.duke.command.Command;

public class Parser {
    public Command parse(String userInput) {
        Parser commandParser = selectParsertType(userInput);

    }
    private Parser selectParsertType(String userInput) {
        String[] commandParts = userInput.split(" ", 2);
        String commandType = commandParts[0];
        switch (commandType) {
        case "client":
            return new ClientParser();
        case "list":
            return new ListParser();
        case "exit":
            return new ExitParser();
        default:
            throw new Exception("Invalid command type");
        }
    }



}
