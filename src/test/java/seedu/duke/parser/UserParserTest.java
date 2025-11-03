package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.EditUserCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class UserParserTest {

    @Test
    void executeAndCreateCommand_validAddCommand_returnsAddCommand() throws FinanceProPlusException {
        UserParser parser = new UserParser("user",
                "add n/John Doe e/john@example.com c/98765432 r/FA-12345");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_validEditCommand_returnsEditUserCommand() throws FinanceProPlusException {
        UserParser parser = new UserParser("user",
                "edit n/Jane Doe e/jane@example.com c/91234567 r/FA-54321");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(EditUserCommand.class, command);
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
                "ADD n/Jane Smith e/jane@example.com c/91234567 r/FA-002");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        UserParser parser = new UserParser("user", "remove n/John e/john@example.com c/91234567 r/FA-003");
        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class,
                parser::executeAndCreateCommand);
        assertEquals("Invalid user command subtype. Please use one of:'add', 'edit', or 'view'.",
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
                () -> new UserParser("client", "add n/John e/john@example.com c/98765432 r/FA-12345"));
        assertEquals("UserParser: type must be user", error.getMessage());
    }
}
