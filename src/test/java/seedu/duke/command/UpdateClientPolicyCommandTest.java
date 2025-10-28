package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.ClientList;
import seedu.duke.client.ArchivedClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;
import seedu.duke.user.UserList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateClientPolicyCommandTest {
    static class SpyClientList extends ClientList {
        boolean updatePolicyForClientCalled = false;
        String receivedArgs;
        boolean shouldThrow = false;

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }
        @Override
        public void updatePolicyForClient(String arguments) throws FinanceProPlusException {
            if (shouldThrow) {
                throw new FinanceProPlusException("Simulated error: Client or Policy not found");
            }
            this.updatePolicyForClientCalled = true;
            this.receivedArgs = arguments;
        }
    }
    static class StubPolicyList extends PolicyList {}
    static class StubMeetingList extends MeetingList {}
    static class StubTaskList extends TaskList {}
    static class StubUserList extends UserList {}
    static class StubArchivedClientList extends ArchivedClientList {}
    private LookUpTable lookUpTable;
    private SpyClientList spyClientList;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        spyClientList = new SpyClientList();
        lookUpTable = new LookUpTable(spyClientList, new StubPolicyList(), new StubMeetingList(),
                new StubTaskList(), new StubUserList(), new StubArchivedClientList());

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_validSubtypeAndArgs_callsUpdatePolicyForClient() throws FinanceProPlusException {
        String args = "id/S123A p/HealthGuard m/250.75";
        UpdateClientPolicyCommand command = new UpdateClientPolicyCommand("client", args);
        command.execute(lookUpTable);
        assertTrue(spyClientList.updatePolicyForClientCalled,
                "The updatePolicyForClient method on ClientList should have been called.");
        assertEquals(args, spyClientList.receivedArgs,
                "The arguments should be passed correctly to the ClientList method.");
    }

    @Test
    void execute_wrongSubtype_throwsInternalErrorException() {
        UpdateClientPolicyCommand command = new UpdateClientPolicyCommand("policy", "some args");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> command.execute(lookUpTable));
        assertEquals("Internal Error: 'client' list is not a valid ClientList.", e.getMessage());
        assertFalse(spyClientList.updatePolicyForClientCalled,
                "updatePolicyForClient should not be called when the subtype is wrong.");
    }

    @Test
    void execute_clientListThrowsException_propagatesException() {
        String args = "id/NONEXISTENT p/SomePolicy e/2025-01-01";
        UpdateClientPolicyCommand command = new UpdateClientPolicyCommand("client", args);
        spyClientList.setShouldThrow(true);
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> command.execute(lookUpTable));
        assertEquals("Simulated error: Client or Policy not found", e.getMessage());
    }

    @Test
    void printExecutionMessage_printsCorrectMessage() {
        UpdateClientPolicyCommand command = new UpdateClientPolicyCommand("client", "");
        String expected = "Policy updated successfully!" + System.lineSeparator() +
                "----------------------------------------------------" + System.lineSeparator();
        command.printExecutionMessage();
        assertEquals(expected, outContent.toString());
    }
}
