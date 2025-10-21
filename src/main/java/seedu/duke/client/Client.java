package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

public class Client {
    private static final String CLIENT_REGEX = "\\s+(?=[a-z]+\\/)";
    private String name;
    private PolicyList policyList;
    private String nric;
    private int phoneNumber;

    public Client(String arguments) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for client creation cannot be null or" +
                " empty";
        policyList = new PolicyList();
        assert policyList != null : "policyList should not be null after initialization";
        Map<String, String> detailsMap = parseClientDetails(arguments);
        List<String> requiredKeys = List.of("n", "c", "id", "p");
        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid client details. Please provide all required fields: "
                        + "n/NAME c/CONTACT id/NRIC p/POLICY");
            }
        }
        name = detailsMap.get("n");
        nric = detailsMap.get("id");
        phoneNumber = Integer.parseInt(detailsMap.get("c"));
        policyList.addPolicy(new Policy(detailsMap.get("p")));
        assert this.name != null && !this.name.isEmpty() : "Client name should be initialized";
        assert this.nric != null && !this.nric.isEmpty() : "Client NRIC should be initialized";
    }
    /**
     * Returns constructor for creating a Client, where the policy is OPTIONAL.
     * It validates required fields (n, c, id) and optionally validates and adds a policy.
     *
     * @param arguments The raw string of client details.
     * @param mainPolicyList The ListContainer holding all company policies for validation.
     * @throws FinanceProPlusException if required details are missing or an optional policy is invalid.
     */
    public Client(String arguments, ListContainer mainPolicyList) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for client creation cannot be null";
        assert mainPolicyList != null : "Main policy list cannot be null for validation";
        this.policyList = new PolicyList();
        assert this.policyList != null : "policyList should not be null after initialization";
        Map<String, String> detailsMap = parseClientDetails(arguments);
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
        if (detailsMap.containsKey("p")) {
            String policyNumberToFind = detailsMap.get("p");
            if (policyNumberToFind.isEmpty()) {
                throw new FinanceProPlusException("Invalid command: Policy number (p/) cannot be empty.");
            }
            PolicyList companyPolicies = (PolicyList) mainPolicyList;
            Optional<Policy> foundPolicyOpt = companyPolicies.findPolicyByName(policyNumberToFind);
            if (foundPolicyOpt.isEmpty()) {
                throw new FinanceProPlusException("Validation Error: Policy '" + policyNumberToFind
                        + "' does not exist. Please add it to the main policy list first.");
            } else {
                this.policyList.addPolicy(foundPolicyOpt.get());
            }
        }
        assert this.name != null && !this.name.isEmpty() : "Client name should be initialized";
        assert this.nric != null && !this.nric.isEmpty() : "Client NRIC should be initialized";
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
    public void addPolicy(Policy policy) {
        if (!this.hasPolicy(policy.getName())) {
            this.policyList.addPolicy(policy);
        }
    }
    /**
     * Checks if the client already owns a policy with the given name.
     * @param policyName The name of the policy to check.
     * @return true if the client already has the policy, false otherwise.
     */
    public boolean hasPolicy(String policyName) {
        return this.policyList.findPolicyByName(policyName).isPresent();
    }
}
