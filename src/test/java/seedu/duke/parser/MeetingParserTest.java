package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MeetingParserTest {
    @Test
    void executeAndCreateCommand_validInput_returnsAddCommand() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "add t/Meeting c/Bob d/17-10-2025 from/14:00 to/16:00");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_validInput_returnsDeleteCommand() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "delete 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "update t/Meeting");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid meeting command subtype", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithoutArgs_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "delete");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid meeting command arguments", exception.getMessage());
    }

}
