package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.ClientPolicy;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    private static final String CLIENT_REGEX = "\\s+(?=[a-z]+\\/)";
    private String name;
    private PolicyList policyList;
    private TaskList todoList;
    private String nric;
    private int phoneNumber;

    /**
     * Returns constructor for creating a Client, where the policy is OPTIONAL.
     * It validates required fields (n, c, id) and optionally validates and adds a policy.
     *
     * @param arguments      The raw string of client details.
     * @param mainPolicyList The ListContainer holding all company policies for validation.
     * @throws FinanceProPlusException if required details are missing or an optional policy is invalid.
     *
     */
    public Client(String arguments, ListContainer mainPolicyList) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for client creation cannot be null";
        assert mainPolicyList != null : "Main policy list cannot be null for validation";
        this.policyList = new PolicyList();
        this.todoList = new TaskList();
        assert this.policyList != null : "policyList should not be null after initialization";
        assert this.todoList != null : "todoList should not be null after initialization";

        Map<String, List<String>> detailsMap = parseClientDetails(arguments);

        initialiseMainDetails(detailsMap);
        initialiseOptionalPolicy(detailsMap, mainPolicyList);
        if (this.name == null || this.name.isEmpty() ||
                this.phoneNumber == 0 ||
                this.nric == null || this.nric.isEmpty()) {
            throw new FinanceProPlusException("Required fields are missing");
        }

        assert this.name != null && !this.name.isEmpty() : "Client name should be initialized";
        assert this.nric != null && !this.nric.isEmpty() : "Client NRIC should be initialized";


    }

    /**
     * Validates and sets the required fields (name, NRIC, phone number) for the client.
     *
     * @param detailsMap The map of parsed arguments.
     * @throws FinanceProPlusException If any required key is missing.
     */
    private void initialiseMainDetails(Map<String, List<String>> detailsMap) throws FinanceProPlusException {
        List<String> requiredKeys = List.of("n", "c", "id");
        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid client details. Required fields are missing. "
                        + "Please provide: n/NAME c/CONTACT id/NRIC");
            }
        }
        this.name = detailsMap.get("n").get(0);
        this.nric = detailsMap.get("id").get(0).toUpperCase();
        String rawContactString = detailsMap.get("c").get(0);
        String sanitizedContactString = rawContactString.replaceAll("\\s+", "");
        String phoneRegex = "^\\d{8}$";
        if (!sanitizedContactString.matches(phoneRegex)) {
            throw new FinanceProPlusException("Invalid contact number.The number must be exactly 8 digits long.");
        }
        this.phoneNumber = Integer.parseInt(sanitizedContactString);

    }

    /**
     * Handles the creation of a "placeholder" ClientPolicy if the 'p/' prefix is provided.
     *
     * @param detailsMap     The map of parsed arguments.
     * @param mainPolicyList The main list of company policies to validate against.
     * @throws FinanceProPlusException If the policy number is empty or the policy doesn't exist.
     */
    private void initialiseOptionalPolicy(Map<String, List<String>> detailsMap, ListContainer mainPolicyList)
            throws FinanceProPlusException {
        if (!detailsMap.containsKey("p")) {
            return;
        }

        PolicyList companyPolicies = (PolicyList) mainPolicyList;
        for (String policyName : detailsMap.get("p")) {
            if (policyName.isEmpty()) {
                throw new FinanceProPlusException("Invalid command: Policy number (p/) cannot be empty.");
            }
            Policy basePolicy = companyPolicies.findPolicyByName(policyName);
            if (basePolicy == null) {
                throw new FinanceProPlusException("Validation Error: Policy '" + policyName
                        + "' does not exist. Please add it to the main policy list first.");
            }
            ClientPolicy placeholderPolicy = new ClientPolicy(basePolicy);
            this.policyList.addPolicy(placeholderPolicy);
        }
    }

    public static Map<String, List<String>> parseClientDetails(String clientDetails) {
        assert clientDetails != null : "Input string for parsing cannot be null";
        Map<String, List<String>> detailsMap = new HashMap<>();
        String trimmedDetails = clientDetails.trim();
        String[] parts = trimmedDetails.split(CLIENT_REGEX);
        for (String part : parts) {
            String[] keyValue = part.split("/", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].trim();
                detailsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            }
        }
        assert detailsMap != null : "The resulting details map should not be null";
        return detailsMap;
    }

    @Override
    public String toString() {
        assert name != null : "Name should not be null when calling toString";
        assert nric != null : "NRIC should not be null when calling toString";
        assert policyList != null : "PolicyList should not be null when calling toString";
        return "Name: " + name + ", ID: " + nric + ", Contact: " + phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    /**
     * Adds a validated policy to the client's personal policy list.
     *
     * @param policy The Policy object to add.
     */
    public void addPolicy(ClientPolicy policy) throws FinanceProPlusException {
        if (!this.hasPolicy(policy.getName())) {
            this.policyList.addPolicy(policy);
        }
    }

    /**
     * Checks if the client already owns a policy with the given name.
     *
     * @param policyName The name of the policy to check.
     * @return true if the client already has the policy, false otherwise.
     */
    public boolean hasPolicy(String policyName) throws FinanceProPlusException {
        return this.policyList.findPolicyByName(policyName) != null;
    }

    public PolicyList getClientPolicyList() {
        return policyList;
    }

    public void viewDetails() {
        System.out.println("-------------------------------------");
        System.out.println("         Client Details");
        System.out.println("-------------------------------------");
        System.out.println("Name: " + this.name);
        System.out.println("NRIC: " + this.nric);
        System.out.println("Contact: " + this.phoneNumber);
        System.out.println("\n--- Policies ---");
        ArrayList<Policy> policies = this.policyList.getPolicyList();
        if (policies.isEmpty()) {
            System.out.println("This client currently has no policies.");
        } else {
            for (int i = 0; i < policies.size(); i++) {
                ClientPolicy policy = (ClientPolicy) policies.get(i);
                System.out.println((i + 1) + ". " + policy.viewDetails());
            }
        }
        System.out.println("\n--- To-Dos ---");
        try {
            this.todoList.listItems();
        } catch (FinanceProPlusException e) {
            System.out.println("Could not display to-dos due to an error: " + e.getMessage());
        }
    }

    /**
     * Finds and removes a policy from this client's policy list by its name.
     *
     * @param policyName The name of the policy to remove.
     * @return true if a policy was found and removed, false otherwise.
     */
    public boolean removePolicyByName(String policyName) {
        PolicyList clientPolicies = this.getClientPolicyList();
        Policy policyToRemove = null;
        for (Policy p : clientPolicies.getPolicyList()) {
            if (p.getName().equals(policyName)) {
                policyToRemove = p;
                break;
            }
        }
        if (policyToRemove != null) {
            clientPolicies.getPolicyList().remove(policyToRemove);
            return true;
        }
        return false;
    }

    /**
     * Returns the todo list for this client.
     *
     * @return The TaskList containing this client's todos.
     */
    public TaskList getTodoList() {
        return this.todoList;
    }

    /**
     * Adds a todo to this client's todo list.
     *
     * @param todoArguments The todo details in the format d/DESCRIPTION by/DUE_DATE
     * @throws FinanceProPlusException If the todo details are invalid.
     */
    public void addTodo(String todoArguments) throws FinanceProPlusException {
        this.todoList.addItem(todoArguments);
    }

    /**
     * Lists all todos for this client.
     *
     * @throws FinanceProPlusException If there's an error listing the todos.
     */
    public void listTodos() throws FinanceProPlusException {
        System.out.println("To-dos for client " + this.name + " (NRIC: " + this.nric + "):");
        this.todoList.listItems();
    }


    public String toStorageString() {
        return String.format("n/%s id/%s c/%s", name, nric, phoneNumber);
    }


    public String[] toCSVRow() {
        String joinedPolicies = policyList.getPolicyList().isEmpty()
                ? "none"
                : policyList.getPolicyList().stream()
                .map(Policy::getName)
                .collect(Collectors.joining(", "));
        return new String[]{name, String.valueOf(phoneNumber), nric, joinedPolicies};
    }

    /**
     * Validates an NRIC based on the format: a letter, followed by 7 digits, and another letter.
     * The method is case-insensitive.
     *
     * @param nric The NRIC string to validate.
     * @return true if the NRIC format is valid, false otherwise.
     */
    public static boolean isValidNric(String nric) {
        if (nric == null) {
            return false;
        }
        String nricRegex = "^[a-zA-Z]\\d{7}[a-zA-Z]$";

        return nric.matches(nricRegex);
    }

    public PolicyList getPolicyList() {
        return this.policyList;
    }
}

