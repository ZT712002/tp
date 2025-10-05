package seedu.duke.parser;

import seedu.duke.exception.FinanceProPlusException;

public class ListParser extends Parser{
    private String commandType;
    private String commandSubtype;
    public ListParser(String type, String commandArgs) throws FinanceProPlusException {
        this.commandType = type;
        String[] commandParts = splitCommand(commandArgs);
        if (commandParts.length > 1){
            throw new FinanceProPlusException("Too many arguments for list command");
        }
        this.commandSubtype = commandParts[0].toLowerCase();
        System.out.println(this.commandSubtype + " " + this.commandType);
    }
}
