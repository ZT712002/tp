package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.SearchCommand;
import seedu.duke.command.ArchiveCommand;
import seedu.duke.command.RestoreCommand;

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
    void executeAndCreateCommand_addCommandWithoutMissingArgs_returnsAddCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "add /n John Doe c/ 12345678 id/");
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
        AssertionError error = assertThrows(AssertionError.class, () -> new ClientParser("client",
                ""));
        assertEquals("Client commands should have arguments", error.getMessage());
    }

    @Test
    void executeAndCreateCommand_searchCommandWithArgs_returnsSearchCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "search T1234567A");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(SearchCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_searchCommandWithoutArgs_returnsSearchCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "search");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(SearchCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_caseInsensitiveSearchSubtype_returnsSearchCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "SEARCH T1234567A");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(SearchCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_archiveCommandWithArgs_returnsArchiveCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "archive 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ArchiveCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_archiveCommandWithoutArgs_returnsArchiveCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "archive");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ArchiveCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_restoreCommandWithArgs_returnsRestoreCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "restore 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(RestoreCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_restoreCommandWithoutArgs_returnsRestoreCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "restore");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(RestoreCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_caseInsensitiveArchiveSubtype_returnsArchiveCommand() throws FinanceProPlusException {
        ClientParser parser = new ClientParser("client", "ARCHIVE 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(ArchiveCommand.class, command);
    }



}
