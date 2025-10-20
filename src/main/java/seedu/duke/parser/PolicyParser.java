package seedu.duke.parser;

import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.FinanceProPlusException;


public class PolicyParser extends Parser{
    private String commandType;
    private String commandSubtype;
    private String arguments;
    public PolicyParser(String type, String commandArgs) {
        this.commandType = type;
        String[] commandParts = splitCommand(commandArgs);
        this.commandSubtype = commandParts[0].toLowerCase();
        this.arguments = commandParts.length > 1 ? commandParts[1] : "";
    }
    @Override
    protected Command executeAndCreateCommand() throws FinanceProPlusException {
        String subtype = this.commandSubtype;
        String args = this.arguments;
        switch (subtype){
        case "add":
            return new AddCommand(commandType, args);
        case "delete":
            return new DeleteCommand(commandType, args);
        default:
            throw new FinanceProPlusException("Invalid command subtype, Please try again");
        }
    }
}

