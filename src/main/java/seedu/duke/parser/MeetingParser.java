package seedu.duke.parser;

import seedu.duke.command.*;
import seedu.duke.exception.FinanceProPlusException;

public class MeetingParser extends Parser {
    private String commandType;
    private String commandSubtype;
    private String arguments;

    public MeetingParser(String type, String commandArgs) {
        assert type.equals("meeting") : "MeetingParser can only be used for meeting commands";
        assert !commandArgs.isEmpty(): "Meeting commands should have arguments";
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
            if (arguments.trim().isEmpty()) {
                throw new FinanceProPlusException("Invalid meeting command arguments");
            }
            return new DeleteCommand(commandType, arguments);
        case "list":
            return new ListCommand(commandType);
        case "forecast":
            return new ForecastCommand(commandType);
        default:
            throw new FinanceProPlusException("Invalid meeting command subtype. Please use one of: "
                    + "'add', 'delete', 'list', or 'forecast'.");
        }
    }
}
