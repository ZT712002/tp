package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.FinanceProPlusException;

public class ListParser extends Parser{
    private String commandSubtype;
    public ListParser(String type, String commandArgs) throws FinanceProPlusException {
        String[] commandParts = splitCommand(commandArgs);
        if (commandParts.length > 1){
            throw new FinanceProPlusException("Too many arguments for list command. Please use this format" +
                    "'list <client/meeting/policy/task/archived>'");
        }
        this.commandSubtype = commandParts[0].toLowerCase();
    }

    @Override
    protected Command executeAndCreateCommand() throws FinanceProPlusException {
        String subtype = commandSubtype;
        boolean isValid = checkIsValid(subtype);
        if(!isValid){
            throw new FinanceProPlusException("Invalid list command subtype. Please use this format" +
                    "'list <client/meeting/policy/task/archived>'");
        }
        return new ListCommand(subtype);
    }

    private boolean checkIsValid(String subtype) {
        return subtype.equals("client") || subtype.equals("meeting") || subtype.equals("policy")
                || subtype.equals("task") || subtype.equals("archived");
    }


}
