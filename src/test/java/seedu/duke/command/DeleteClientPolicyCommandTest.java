package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteClientPolicyCommandTest {

    private LookUpTable lookUpTable;
    private ClientList clientList;
    private ListContainer mainPolicyList;
    private final String clientNric = "T1234567A";

    @BeforeEach
    void setUp() throws FinanceProPlusException {

        clientList = new ClientList();
        mainPolicyList = new PolicyList();
        lookUpTable = new LookUpTable(clientList, (PolicyList) mainPolicyList, null,
                null,null,null);


        ((PolicyList) mainPolicyList).addPolicy(new Policy("n/Health d/Basic", true));
        ((PolicyList) mainPolicyList).addPolicy(new Policy("n/Life d/Premium", true));


        clientList.addItem("n/Test Client c/12345678 id/" + clientNric, mainPolicyList);


        String addPolicyArgs1 = "id/" + clientNric + " p/Health s/01-01-2024 e/01-01-2025 m/100";
        String addPolicyArgs2 = "id/" + clientNric + " p/Life s/01-01-2024 e/01-01-2025 m/200";
        clientList.addPolicyToClient(addPolicyArgs1, mainPolicyList);
        clientList.addPolicyToClient(addPolicyArgs2, mainPolicyList);
    }

    @Test
    void execute_validArgs_deletesPolicySuccessfully() throws FinanceProPlusException {

        String commandArgs = "id/" + clientNric + " i/1";
        DeleteClientPolicyCommand command = new DeleteClientPolicyCommand("client", commandArgs);

        Client client = clientList.findClientByNric(clientNric);
        assertEquals(2, client.getClientPolicyList().getPolicyList().size());

        command.execute(lookUpTable);


        assertEquals(1, client.getClientPolicyList().getPolicyList().size());
        assertEquals(false, client.hasPolicy("Health"));
        assertTrue(client.hasPolicy("Life"));
    }

    @Test
    void execute_clientListIsWrongType_throwsException() {

        LookUpTable badLookUpTable = new LookUpTable(null, (PolicyList) mainPolicyList, null,
                null, null,null);
        String commandArgs = "id/" + clientNric + " i/1";
        DeleteClientPolicyCommand command = new DeleteClientPolicyCommand("client", commandArgs);

        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(badLookUpTable));
        assertEquals("Internal Error: 'client' list is not a valid ClientList.", e.getMessage());
    }
}
