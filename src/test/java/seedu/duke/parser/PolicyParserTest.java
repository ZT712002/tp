package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PolicyParserTest {

    @Test
    void executeAndCreateCommand_addCommandWithArgs_returnsAddCommand() throws FinanceProPlusException {
        PolicyParser parser = new PolicyParser("policy", "add n/Life Insurance d/Full coverage");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_addCommandWithoutArgs_returnsAddCommand() throws FinanceProPlusException {
        PolicyParser parser = new PolicyParser("policy", "add");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_deleteCommandWithArgs_returnsDeleteCommand() throws FinanceProPlusException {
        PolicyParser parser = new PolicyParser("policy", "delete 1");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(DeleteCommand.class, command);
    }


    @Test
    void executeAndCreateCommand_caseInsensitiveSubtype_returnsCorrectCommand() throws FinanceProPlusException {
        PolicyParser parser = new PolicyParser("policy", "ADD n/Health Insurance d/Medical coverage");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(AddCommand.class, command);
    }


    @Test
    void executeAndCreateCommand_commandWithoutArgs_returnsCorrectCommand() throws FinanceProPlusException {
        PolicyParser parser = new PolicyParser("policy", "delete");
        Command command = parser.executeAndCreateCommand();
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void executeAndCreateCommand_invalidSubtype_throwsException() {
        PolicyParser parser = new PolicyParser("policy", "update n/Life Insurance");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid command subtype, Please try again", exception.getMessage());
    }

    @Test
    void constructor_emptyCommandArgs_throwsException() {
        PolicyParser parser = new PolicyParser("policy", "");
        Exception exception = assertThrows(FinanceProPlusException.class, parser::executeAndCreateCommand);
        assertEquals("Invalid command subtype, Please try again", exception.getMessage());
    }
}

