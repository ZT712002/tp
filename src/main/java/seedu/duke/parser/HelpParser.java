package seedu.duke.parser;

import seedu.duke.command.ExitCommand;
import seedu.duke.command.HelpCommand;

public class HelpParser extends Parser {
    @Override
    protected HelpCommand executeAndCreateCommand(){
        return new HelpCommand();
    }
}
