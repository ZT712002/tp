package seedu.duke.parser;

import seedu.duke.command.ExitCommand;

public class ExitParser extends Parser{
    @Override
    protected ExitCommand executeAndCreateCommand(){
        return new ExitCommand();
    }
}
