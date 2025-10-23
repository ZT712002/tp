package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.ClientPolicy;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Client {
    private static final String CLIENT_REGEX = "\\s+(?=[a-z]+\\/)";
    private String name;
    private PolicyList policyList;
    private String nric;
    private int phoneNumber;

    /**
     * Returns constructor for creating a Client, where the policy is OPTIONAL.
     * It validates required fields (n, c, id) and optionally validates and adds a policy.
     *
     * @param arguments The raw string of client details.
     * @param mainPolicyList The ListContainer holding all company policies for validation.
     * @throws FinanceProPlusException if required details are missing or an optional policy is invalid.
     *
     */
    public Client(String arguments, ListContainer mainPolicyList) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for client creation cannot be null";
        assert mainPolicyList != null : "Main policy list cannot be null for validation";
        this.policyList = new PolicyList();
        assert this.policyList != null : "policyList should not be null after initialization";
        Map<String, String> detailsMap = parseClientDetails(arguments);
        initialiseMainDetails(detailsMap);
        initialiseOptionalPolicy(detailsMap, mainPolicyList);
        assert this.name != null && !this.name.isEmpty() : "Client name should be initialized";
        assert this.nric != null && !this.nric.isEmpty() : "Client NRIC should be initialized";
    }

    /**
     * Validates and sets the required fields (name, NRIC, phone number) for the client.
     *
     * @param detailsMap The map of parsed arguments.
     * @throws FinanceProPlusException If any required key is missing.
     */
    private void initialiseMainDetails(Map<String, String> detailsMap) throws FinanceProPlusException {
        List<String> requiredKeys = List.of("n", "c", "id");
        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid client details. Required fields are missing. "
                        + "Please provide: n/NAME c/CONTACT id/NRIC");
            }
        }
        this.name = detailsMap.get("n");
        this.nric = detailsMap.get("id");
        this.phoneNumber = Integer.parseInt(detailsMap.get("c"));
    }

    /**
     * Handles the creation of a "placeholder" ClientPolicy if the 'p/' prefix is provided.
     *
     * @param detailsMap The map of parsed arguments.
     * @param mainPolicyList The main list of company policies to validate against.
     * @throws FinanceProPlusException If the policy number is empty or the policy doesn't exist.
     */
    private void initialiseOptionalPolicy(Map<String, String> detailsMap, ListContainer mainPolicyList)
            throws FinanceProPlusException {
        if (detailsMap.containsKey("p")) {
            String policyNumberToFind = detailsMap.get("p");
            if (policyNumberToFind.isEmpty()) {
                throw new FinanceProPlusException("Invalid command: Policy number (p/) cannot be empty.");
            }
            PolicyList companyPolicies = (PolicyList) mainPolicyList;
            Policy basePolicy = companyPolicies.findPolicyByName(policyNumberToFind);
            if(basePolicy == null){
                throw new FinanceProPlusException("Validation Error: Policy '" + policyNumberToFind
                        + "' does not exist. Please add it to the main policy list first.");
            }
            ClientPolicy placeholderPolicy = new ClientPolicy(basePolicy);
            this.policyList.addPolicy(placeholderPolicy);
        }
    }


    public static Map<String, String> parseClientDetails(String clientDetails) {
        assert clientDetails != null : "Input string for parsing cannot be null";
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = clientDetails.trim();
        String[] parts = trimmedDetails.split(CLIENT_REGEX);
        for (String part : parts) {
            String[] keyValue = part.split("/", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].trim();
                detailsMap.put(key, value);
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
        return "Name: " + name + ", ID: " + nric + ", Contact: " + phoneNumber + ", Policies: " +
                policyList.toString();
    }

    public String getName() {
        return name;
    }
    public String getNric() {
        return nric;
    }
    /**
     * Adds a validated policy to the client's personal policy list.
     * @param policy The Policy object to add.
     */
    public void addPolicy(ClientPolicy policy) throws FinanceProPlusException {
        if (!this.hasPolicy(policy.getName())) {
            this.policyList.addPolicy(policy);
        }
    }
    /**
     * Checks if the client already owns a policy with the given name.
     * @param policyName The name of the policy to check.
     * @return true if the client already has the policy, false otherwise.
     */
    public boolean hasPolicy(String policyName) throws FinanceProPlusException {
        return this.policyList.findPolicyByName(policyName) != null;
    }

    public PolicyList getPolicyList() {
        return policyList;
    }

    public void viewDetails(){
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
    }
}
