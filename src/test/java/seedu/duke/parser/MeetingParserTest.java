package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.ForecastCommand;
import seedu.duke.exception.FinanceProPlusException;

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
    void executeAndCreateCommand_startTimeAfterEndTime_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "add t/Meeting c/Bob d/17-10-2025 from/16:00 to/14:00");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Start time (16:00) must be before end time (14:00)", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_sameStartAndEndTime_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "add t/Meeting c/Bob d/17-10-2025 from/14:00 to/14:00");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Start time (14:00) must be before end time (14:00)", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "update t/Meeting");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid meeting command subtype. Please use one of: "
                + "'add', 'delete', 'list' or 'forecast'.", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithoutArgs_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "delete");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid meeting command arguments", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_validList_returnsListCommand() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "list");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_validForecast_returnsForecastCommand() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "forecast");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ForecastCommand.class, command);
    }

}
