package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskParserTest {

    @Test
    void executeAndCreateCommand_addCommandWithArgs_returnsAddCommand() throws FinanceProPlusException {
        TaskParser parser = new TaskParser("task", "add d/Review portfolio by/15-03-2024");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_addCommandWithoutArgs_returnsAddCommand() throws FinanceProPlusException {
        TaskParser parser = new TaskParser("task", "add");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithArgs_returnsDeleteCommand() throws FinanceProPlusException {
        TaskParser parser = new TaskParser("task", "delete 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithoutArgs_throwsException() {
        TaskParser parser = new TaskParser("task", "delete");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid task command arguments", exception.getMessage());
    }

    @Test
    void executeAndCreateCommand_caseInsensitiveSubtype_returnsCorrectCommand() throws FinanceProPlusException {
        TaskParser parser = new TaskParser("task", "ADD d/Review portfolio by/15-03-2024");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        TaskParser parser = new TaskParser("task", "update d/Review portfolio");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid task command subtype. Please use one of: 'add' or 'delete'. "
                + "To list tasks, use 'list task'.", 
                exception.getMessage());
    }

    @Test
    void constructor_emptyCommandArgs_throwsException() {
        TaskParser parser = new TaskParser("task", "");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid task command subtype. Please use one of: 'add' or 'delete'. "
                + "To list tasks, use 'list task'.", 
                exception.getMessage());
    }
}

