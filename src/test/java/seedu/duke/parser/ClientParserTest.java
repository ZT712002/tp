package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientParserTest {

    @Test
    void executeAndCreateCommand_addCommandWithArgs_returnsAddCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "add /n John Doe c/ 12345678 id/ Txx03912A" +
                " p/ 3120931");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithArgs_returnsDeleteCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "delete 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(DeleteCommand.class, command);
        DeleteCommand deleteCommand = (DeleteCommand) command;
    }

    @Test
    void executeAndCreateCommand_caseInsensitiveSubtype_returnsCorrectCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "ADD /n Jane Doe");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_commandWithoutArgs_returnsCorrectCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "delete");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(DeleteCommand.class, command);

    }


    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        ClientParser parser = new ClientParser("client", "update /n John");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);


        assertEquals("Invalid command subtype, Please try again", exception.getMessage());
    }

    @Test
    void constructor_emptyCommandArgs_throwsException() {
        ClientParser parser = new ClientParser("client", "");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid command subtype, Please try again", exception.getMessage());
    }
}