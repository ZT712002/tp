package seedu.duke.parser;

import seedu.duke.command.AddCommand;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.FinanceProPlusException;

public class UserParser extends Parser {
    private String commandType;
    private String commandSubtype;
    private String arguments;

    public UserParser(String type, String commandArgs) {
        this.commandType = type;
        String[] commandParts = splitCommand(commandArgs);
        this.commandSubtype = commandParts[0].toLowerCase();
        this.arguments = commandParts.length > 1 ? commandParts[1] : "";
    }

    @Override
    protected Command executeAndCreateCommand() throws FinanceProPlusException {
        switch (commandSubtype) {
        case "add":
            return new AddCommand(commandType, arguments);
        case "delete":
            return new DeleteCommand(commandType, arguments);
        case "view":
            return new seedu.duke.command.ListCommand(commandType);
        default:
            throw new FinanceProPlusException("Invalid user command subtype. Please use one of:"
                    + "'add', 'delete', or 'view'.");

        }
    }
}
