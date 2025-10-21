package seedu.duke.parser;

import seedu.duke.command.AddCommand;
import seedu.duke.command.AddPolicyCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.exception.FinanceProPlusException;


public class ClientParser extends Parser{
    private String commandType;
    private String commandSubtype;
    private String arguments;
    public ClientParser(String type, String commandArgs) {
        assert type.equals("client") : "ClientParser can only be used for client commands";
        assert !commandArgs.isEmpty(): "Client commands should have arguments";
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
        case "addpolicy":
            return new AddPolicyCommand(commandType, args);
        default:
            throw new FinanceProPlusException("Invalid command subtype, Please try again");
        }
    }
}
