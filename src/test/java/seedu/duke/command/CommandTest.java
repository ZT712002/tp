package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CommandTest {

    @Test
    void constructor_createsCommandInstance() {
        Command command = new Command();
        assertNotNull(command, "The command object should not be null after instantiation.");
    }

    @Test
    void execute_unimplementedBaseCommand_throwsException() {
        Command command = new Command();
        LookUpTable lookUpTable = null;
        String expectedMessage = "This command should be implemented by child classes";
        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class, () -> {
            command.execute(lookUpTable);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void printExecutionMessage_unimplementedBaseCommand_throwsException() {
        Command command = new Command();
        String expectedMessage = "This command should be implemented by child classes";

        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class, () -> {
            command.printExecutionMessage();
        });
        assertEquals(expectedMessage, exception.getMessage());
    }
}
