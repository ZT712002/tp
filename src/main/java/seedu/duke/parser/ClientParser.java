package seedu.duke.parser;

import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.subclasses.CommandSubClass;

public class ClientParser extends Parser{
    private String commandSubtype;
    private String arguments;
    public ClientParser(String Arguments) {
        String[] commandParts = splitCommand(Arguments);
        this.commandSubtype = commandParts[0];
        this.arguments = commandParts[1];
    }
    @Override
    protected Command executeAndCreateCommand() {
        String subtype = this.commandSubtype;
        String args = this.arguments;
        switch (subtype){
        case "add":
            return new AddCommand(args, CommandSubClass.CLIENT);
            break;
        case "delete":
            return new DeleteCommand(args);
            break;
        }
    }
}
