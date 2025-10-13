package seedu.duke.client;

import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Client {
    private static final String CLIENT_REGEX = "\\s+(?=[a-z]+\\/)";
    private String name;
    private PolicyList policyList;
    private String nric;
    private int phoneNumber;

    public Client(String arguments) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for client creation cannot be null or empty";
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

}
