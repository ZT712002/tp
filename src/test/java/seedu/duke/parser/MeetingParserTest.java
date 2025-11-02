package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
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
    void executeAndCreateCommand_startTimeAfterEndTime_throwsException() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "add t/Meeting c/Bob d/17-10-2025 from/16:00 to/14:00");
        Command command = parser.executeAndCreateCommand();
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            command.execute(new seedu.duke.container.LookUpTable(
                new seedu.duke.client.ClientList(),
                new seedu.duke.policy.PolicyList(),
                new seedu.duke.meeting.MeetingList(),
                new seedu.duke.task.TaskList(),
                new seedu.duke.user.UserList(),
                new seedu.duke.client.ArchivedClientList()
            ));
        });
        assertEquals("Start time (16:00) must be before end time (14:00)", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_sameStartAndEndTime_throwsException() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "add t/Meeting c/Bob d/17-10-2025 from/14:00 to/14:00");
        Command command = parser.executeAndCreateCommand();
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            command.execute(new seedu.duke.container.LookUpTable(
                new seedu.duke.client.ClientList(),
                new seedu.duke.policy.PolicyList(),
                new seedu.duke.meeting.MeetingList(),
                new seedu.duke.task.TaskList(),
                new seedu.duke.user.UserList(),
                new seedu.duke.client.ArchivedClientList()
            ));
        });
        assertEquals("Start time (14:00) must be before end time (14:00)", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "update t/Meeting");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid meeting command subtype. Please use one of: "
                + "'add', 'delete' or 'forecast'.", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithoutArgs_throwsException() {
        MeetingParser parser = new MeetingParser("meeting", "delete");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid meeting command arguments", exception.getMessage());
    }



    @Test
    void executeAndCreateCommand_validForecast_returnsForecastCommand() throws FinanceProPlusException {
        MeetingParser parser = new MeetingParser("meeting", "forecast");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ForecastCommand.class, command);
    }

}
