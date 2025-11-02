package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserParserTest {
    @Test
    void executeAndCreateCommand_validAddCommand_returnsAddCommand() throws FinanceProPlusException {
        UserParser parser = new UserParser("user",
                "add n/John Doe e/john@example.com p/98765432 r/REP001");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }


    @Test
    void executeAndCreateCommand_validViewCommand_returnsListCommand() throws FinanceProPlusException {
        UserParser parser = new UserParser("user", "view");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_caseInsensitiveSubtype_returnsCorrectCommand() throws FinanceProPlusException {
        UserParser parser = new UserParser("user",
                "ADD n/Jane Smith e/jane@example.com p/91234567 r/REP002");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        UserParser parser = new UserParser("user", "update n/John e/john@example.com p/91234567 r/REP003");
        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class,
                parser::executeAndCreateCommand);
        assertEquals("Invalid user command subtype. Please use one of:'add', 'edit' or 'view'.",
                exception.getMessage());
    }

    @Test
    void constructor_emptyCommandArgs_throwsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
                () -> new UserParser("user", ""));
        assertEquals("User commands should have arguments", error.getMessage());
    }

    @Test
    void constructor_invalidType_throwsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
                () -> new UserParser("client", "add n/John e/john@example.com p/98765432 r/REP001"));
        assertEquals("UserParser: type must be user", error.getMessage());
    }
}
