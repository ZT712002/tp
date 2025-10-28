package seedu.duke.parser;

import seedu.duke.command.AddCommand;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.FinanceProPlusException;

public class TaskParser extends Parser {
    private String commandType;
    private String commandSubtype;
    private String arguments;

    public TaskParser(String type, String commandArgs) {
        assert type.equals("task") : "TaskParser can only be used for task commands";
        this.commandType = type;
        if (commandArgs.isEmpty()) {
            this.commandSubtype = "";
            this.arguments = "";
        } else {
            String[] commandParts = splitCommand(commandArgs);
            this.commandSubtype = commandParts[0].toLowerCase();
            this.arguments = commandParts.length > 1 ? commandParts[1] : "";
        }
    }

    @Override
    protected Command executeAndCreateCommand() throws FinanceProPlusException {
        switch (commandSubtype) {
        case "add":
            return new AddCommand(commandType, arguments);
        case "delete":
            if (arguments.trim().isEmpty()) {
                throw new FinanceProPlusException("Invalid task command arguments");
            }
            return new DeleteCommand(commandType, arguments);
        default:
            throw new FinanceProPlusException("Invalid task command subtype. Please use one of: "
                    + "'add' or 'delete'. To list tasks, use 'list task'.");
        }
    }
}

