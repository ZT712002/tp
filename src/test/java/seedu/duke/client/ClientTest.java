package seedu.duke.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.ClientPolicy;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class   ClientTest {

    private ListContainer mainPolicyList;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Client client;
    private static class StubPolicy extends Policy {
        public StubPolicy(String name) throws FinanceProPlusException {
            super("n/" + name + " d/details", false);
        }
    }


    private static class StubClientPolicy extends ClientPolicy {
        public StubClientPolicy(Policy basePolicy) throws FinanceProPlusException {
            super(basePolicy);
        }
    }


    @BeforeEach
    void setUp() throws FinanceProPlusException {
        mainPolicyList = new PolicyList();
        Policy healthPolicy = new Policy("n/1234 d/Health Test", true);
        Policy lifePolicy = new Policy("n/1233 d/LifeTest", true);
        ((PolicyList) mainPolicyList).addPolicy(healthPolicy);
        ((PolicyList) mainPolicyList).addPolicy(lifePolicy);
        client = new Client("n/Test Client c/12345678 id/T1234567A", mainPolicyList);

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
    @Test
    void getTodoList_onNewClient_returnsNonNullTaskList() {
        TaskList todoList = client.getTodoList();
        assertNotNull(todoList, "The returned TaskList should not be null.");
    }

    @Test
    void getTodoList_calledMultipleTimes_returnsSameInstance() {
        TaskList list1 = client.getTodoList();
        TaskList list2 = client.getTodoList();
        assertSame(list1, list2, "Consecutive calls to getTodoList() should return the same instance.");
    }



    @Test
    void constructor_validArgsNoPolicy_clientCreatedSuccessfully() throws FinanceProPlusException {
        String args = " n/John Doe  c/12345678  id/S1234567A ";
        Client client = new Client(args, mainPolicyList);

        assertNotNull(client);
        assertEquals("John Doe", client.getName());
        assertEquals("S1234567A", client.getNric());
        assertTrue(client.getClientPolicyList().getPolicyList().isEmpty());
    }

    @Test
    void removePolicyByName_policyDoesNotExist_returnsFalse() throws FinanceProPlusException {
        Policy basePolicy = new StubPolicy("HealthShield");
        client.addPolicy(new StubClientPolicy(basePolicy));
        assertEquals(1, client.getPolicyList().getPolicyList().size());
        boolean result = client.removePolicyByName("LifeInsurance");
        assertFalse(result, "Method should return false when the policy to remove is not found.");
        assertEquals(1, client.getPolicyList().getPolicyList().size(), "Policy list size" +
                " should remain unchanged.");
    }

    @Test
    void removePolicyByName_clientHasNoPolicies_returnsFalse() {
        assertEquals(0, client.getPolicyList().getPolicyList().size());
        boolean result = client.removePolicyByName("AnyPolicy");
        assertFalse(result, "Method should return false when the client's policy list is empty.");
    }

    @Test
    void removePolicyByName_caseSensitiveName_returnsFalse() throws FinanceProPlusException {
        Policy basePolicy = new StubPolicy("HealthShield"); // Note the capitalization
        client.addPolicy(new StubClientPolicy(basePolicy));
        assertEquals(1, client.getPolicyList().getPolicyList().size());
        boolean result = client.removePolicyByName("healthshield");
        assertFalse(result, "Removal should be case-sensitive and return false for a mismatched case.");
        assertEquals(1, client.getPolicyList().getPolicyList().size(), "Policy should not be removed if case does not match.");
    }

    @Test
    void constructor_validArgsWithExistingPolicy_clientCreatedWithPolicy() throws FinanceProPlusException {
        String args = "n/Jane Doe c/87654321 id/S9876543B";
        Client client = new Client(args, mainPolicyList);
        assertNotNull(client);
        assertEquals("Jane Doe", client.getName());
        assertEquals("S9876543B", client.getNric());

    }

    @Test
    void constructor_missingName_throwsException() {
        String args = "c/12345678 id/S1234567A";
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> new Client(args, mainPolicyList));
        assertTrue(e.getMessage().contains("Invalid format"));
    }

    @Test
    void constructor_missingContact_throwsException() {
        String args = "n/John Doe id/S1234567A";
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> new Client(args, mainPolicyList));
        assertTrue(e.getMessage().contains("Invalid format"));
    }

    @Test
    void constructor_missingNric_throwsException() {
        String args = "n/John Doe c/12345678";
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> new Client(args, mainPolicyList));
        assertTrue(e.getMessage().contains("Invalid format"));
    }

    @Test
    void constructor_emptyFieldValue_throwsException() {
        String args = "n/ c/12345678 id/S1234567A";
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> new Client(args, mainPolicyList));
        assertTrue(e.getMessage().contains("Required fields are missing"));
    }



    @Test
    void constructor_nullArguments_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Client(null, mainPolicyList));
    }

    @Test
    void constructor_nullPolicyList_throwsAssertionError() {
        String args = "n/John Doe c/12345678 id/S1234567A";
        assertThrows(AssertionError.class, () -> new Client(args, null));
    }


    @Test
    void parseClientDetails_standardInput_returnsCorrectMap() {
        String details = "n/John Doe c/12345678 id/S1234567A p/1234";
        Map<String, List<String>> detailsMap = Client.parseClientDetails(details);
        assertEquals(4, detailsMap.size());
        assertEquals("John Doe", detailsMap.get("n").get(0));
        assertEquals("12345678", detailsMap.get("c").get(0));
        assertEquals("S1234567A", detailsMap.get("id").get(0));
    }

    @Test
    void parseClientDetails_differentOrder_returnsCorrectMap() {
        String details = "id/S1234567A c/12345678 n/John Doe";
        Map<String, List<String>> detailsMap = Client.parseClientDetails(details);
        assertEquals(3, detailsMap.size());
        assertEquals("John Doe", detailsMap.get("n").get(0));
        assertEquals("12345678", detailsMap.get("c").get(0));
        assertEquals("S1234567A", detailsMap.get("id").get(0));
    }

    @Test
    void parseClientDetails_extraWhitespace_returnsTrimmedValues() {
        String details = " n/  John Doe   c/ 12345678  id/S1234567A ";
        Map<String, List<String>> detailsMap = Client.parseClientDetails(details);
        assertEquals(3, detailsMap.size());
        assertEquals("John Doe", detailsMap.get("n").get(0));
        assertEquals("12345678", detailsMap.get("c").get(0));
        assertEquals("S1234567A", detailsMap.get("id").get(0));
    }

    @Test
    void parseClientDetails_emptyInput_returnsEmptyMap() {
        String details = "";
        Map<String, List<String>> detailsMap = Client.parseClientDetails(details);
        assertTrue(detailsMap.isEmpty());
    }

    @Test
    void parseClientDetails_emptyValueForKey_returnsKeyWithEmptyString() {
        String details = "n/ c/123";
        Map<String, List<String>> detailsMap = Client.parseClientDetails(details);
        assertEquals(2, detailsMap.size());
        assertEquals("", detailsMap.get("n").get(0));
        assertEquals("123", detailsMap.get("c").get(0));
    }

    @Test
    void parseClientDetails_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> Client.parseClientDetails(null));
    }



    @Test
    void addPolicy_newPolicy_addsSuccessfully() throws FinanceProPlusException {
        Client client = new Client("n/Test c/11124567 id/T1111111A", mainPolicyList);
        assertTrue(client.getClientPolicyList().getPolicyList().isEmpty());

        Policy lifePolicyInfo = ((PolicyList) mainPolicyList).findPolicyByName("1234");
        ClientPolicy clientLifePolicy = new ClientPolicy(lifePolicyInfo);

        client.addPolicy(clientLifePolicy);
        assertEquals(1, client.getClientPolicyList().getPolicyList().size());
        assertTrue(client.hasPolicy("1234"));
    }


    @Test
    void hasPolicy_policyDoesNotExist_returnsFalse() throws FinanceProPlusException {
        Client client = new Client("n/Test c/1112 4567 id/T1111111A", mainPolicyList);
        assertFalse(client.hasPolicy("1234"));
    }

    @Test
    void toString_clientWithNoPolicies_returnsCorrectString() throws FinanceProPlusException {
        Client client = new Client("n/John Doe c/12345678 id/S1234567A", mainPolicyList);
        String expected = "Name: John Doe, ID: S1234567A, Contact: 12345678, Policies: 0, To-Dos: 0";
        assertEquals(expected, client.toString());
    }


    @Test
    void viewDetails_clientWithNoPolicies_printsCorrectly() throws FinanceProPlusException {
        Client client = new Client("n/View Test c/9999 1111 id/V9999991A", mainPolicyList);
        client.viewDetails();
        String output = outContent.toString();

        assertTrue(output.contains("Name: View Test"));
        assertTrue(output.contains("NRIC: V9999991A"));
        assertTrue(output.contains("Contact: 999"));
        assertTrue(output.contains("This client currently has no policies."));
    }

    @Test
    void viewDetails_clientWithPolicies_printsCorrectly() throws FinanceProPlusException {
        Client client = new Client("n/View Test c/9991 2345 id/V9999991A", mainPolicyList);
        client.viewDetails();
        String output = outContent.toString();

        assertTrue(output.contains("Name: View Test"));
        assertTrue(output.contains("NRIC: V9999991A"));
        assertTrue(output.contains("Contact: 999"));
    }


}
