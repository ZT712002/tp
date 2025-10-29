package seedu.duke.parser;

import seedu.duke.command.*;
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
        case "updatepolicy":
            return new UpdateClientPolicyCommand(commandType,args);
        case "deletepolicy":
            return new DeleteClientPolicyCommand(commandType,args);
        case "view":
            return new ViewClientCommand(commandType,args);
        case "addtodo":
            return new AddClientTodoCommand(commandType, args);
        case "listtodos":
            return new ListClientTodosCommand(commandType, args);
        case "search":
            return new SearchCommand(commandType, args);
        case "archive":
            return new ArchiveCommand(commandType, args);
        case "restore":
            return new RestoreCommand(commandType, args);
        case "listarchived":
            return new ListCommand("archived");
        default:
            throw new FinanceProPlusException("Invalid command subtype, Please try again");
        }
    }
}
