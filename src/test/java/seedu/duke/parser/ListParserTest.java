package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.Command;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListParserTest {

    @Test
    void executeAndCreateCommand_validClientSubtype_returnsListCommand() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "client");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);

    }

    @Test
    void executeAndCreateCommand_validTaskSubtype_returnsListCommand() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "task");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void constructor_caseInsensitiveSubtype_convertsToLowerCase() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "CLIENT");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "appointment");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        String expectedMessage = "Invalid list command subtype. Please use this format" +
                "'list <client/meeting/policy/task/archived>'";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructor_tooManyArguments_throwsException() {
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            new ListParser("list", "client details");
        });
        String expectedMessage = "Too many arguments for list command. Please use this format" +
                "'list <client/meeting/policy/task/archived>'";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_emptySubtype_throwsException() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        String expectedMessage = "Invalid list command subtype. Please use this format" +
                "'list <client/meeting/policy/task/archived>'";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_validMeetingSubtype_returnsListCommand() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "meeting");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_validArchivedSubtype_returnsListCommand() throws FinanceProPlusException {
        ListParser parser = new ListParser("list", "archived");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);
    }
}
