package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.ClientList;
import seedu.duke.client.ArchivedClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;
import seedu.duke.user.UserList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddPolicyCommandTest {


    static class SpyClientList extends ClientList {
        boolean addPolicyToClientCalled = false;
        String receivedArgs;
        ListContainer receivedPolicyList;
        boolean shouldThrow = false;

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }

        @Override
        public void addPolicyToClient(String arguments, ListContainer mainPolicyList) throws FinanceProPlusException {
            if (shouldThrow) {
                throw new FinanceProPlusException("Simulated validation error from ClientList");
            }
            this.addPolicyToClientCalled = true;
            this.receivedArgs = arguments;
            this.receivedPolicyList = mainPolicyList;
        }
    }


    static class StubPolicyList extends PolicyList {}
    static class StubMeetingList extends MeetingList {}
    static class StubUserList extends UserList {}
    static class StubArchivedClientList extends ArchivedClientList {}


    private LookUpTable lookUpTable;
    private SpyClientList spyClientList;
    private StubPolicyList stubPolicyList;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        spyClientList = new SpyClientList();
        stubPolicyList = new StubPolicyList();
        lookUpTable = new LookUpTable(spyClientList, stubPolicyList, new StubMeetingList(), new StubUserList(),
                new StubArchivedClientList());

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_validSubtypeAndArgs_callsAddPolicyToClientCorrectly() throws FinanceProPlusException {
        String args = "id/S123 p/Health s/2023-01-01 e/2024-01-01 m/100";
        AddPolicyCommand command = new AddPolicyCommand("client", args);
        command.execute(lookUpTable);
        assertTrue(spyClientList.addPolicyToClientCalled,
                "The ClientList.addPolicyToClient method should have been called.");
        assertEquals(args, spyClientList.receivedArgs,
                "The arguments passed to the command should be passed to the list.");
        assertSame(stubPolicyList, spyClientList.receivedPolicyList,
                "The main policy list from the LookUpTable should be passed to the method.");
    }

    @Test
    void execute_wrongSubtype_throwsInternalErrorException() {
        AddPolicyCommand command = new AddPolicyCommand("policy", "some args");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> command.execute(lookUpTable));
        assertEquals("Internal Error: 'client' list is not a ClientList.", e.getMessage());
        assertFalse(spyClientList.addPolicyToClientCalled,
                "ClientList method should NOT be called if subtype is wrong.");
    }

    @Test
    void execute_underlyingMethodThrowsException_propagatesException() {
        AddPolicyCommand command = new AddPolicyCommand("client", "invalid args");
        spyClientList.setShouldThrow(true);
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> command.execute(lookUpTable));
        assertEquals("Simulated validation error from ClientList", e.getMessage());
    }

    @Test
    void printExecutionMessage_printsCorrectMessage() {
        AddPolicyCommand command = new AddPolicyCommand("client", "");
        String expected = "Policy/Policies added successfully to the client!" + System.lineSeparator() +
                "----------------------------------------------------" + System.lineSeparator();
        command.printExecutionMessage();
        assertEquals(expected, outContent.toString());
    }
}
