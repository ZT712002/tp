package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.exception.FinanceProPlusException;

public class Parser {
    public Command parse(String userInput) throws FinanceProPlusException {
        Parser commandParser = selectParserType(userInput);
        return commandParser.executeAndCreateCommand();
    }

    protected Command executeAndCreateCommand() throws FinanceProPlusException {
        throw new FinanceProPlusException("Child classes to fulfill this method");
    }

    private Parser selectParserType(String userInput) throws FinanceProPlusException {
        String[] commandParts = splitCommand(userInput);
        String commandType = commandParts[0];
        String commandArgs = commandParts.length > 1 ? commandParts[1] : "";
        switch (commandType) {
        case "client":
            return new ClientParser(commandArgs);
        case "list":
            return new ListParser(commandArgs);
        case "exit":
            return new ExitParser();
        default:
            throw new FinanceProPlusException("Invalid command type");
        }
    }
    protected String[] splitCommand(String userInput) {
        return userInput.split(" ", 2);
    }



}
