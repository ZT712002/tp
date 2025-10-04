package seedu.duke.client;

import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Client {
    private static final String CLIENT_REGEX = "\\\\s+(?=[a-z]+\\\\/)";
    private String name;
    private PolicyList policyList;

    public String getNric() {
        return nric;
    }

    private String nric;
    private int phoneNumber;
    public Client(String arguments) throws FinanceProPlusException {
        policyList = new PolicyList();
        Map<String, String> detailsMap = parseClientDetails(arguments);
        List<String> requiredKeys = List.of("n", "c", "id", "p");
        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Missing or empty required field: " + key);
            }
        }
        name = detailsMap.get("n");
        nric = detailsMap.get("id");
        phoneNumber = Integer.parseInt(detailsMap.get("c"));
        policyList.addPolicy(new Policy(detailsMap.get("p")));
    }


    public static Map<String, String> parseClientDetails(String clientDetails) {
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = clientDetails.trim();
        String[] parts = trimmedDetails.split("\\s+(?=[a-z]+\\/)");
        for (String part : parts) {
            String[] keyValue = part.split("/", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].trim();
                detailsMap.put(key, value);
            }
        }
        return detailsMap;
    }
    public String getName() {
        return name;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + nric + ", Contact: " + phoneNumber + ", Policies: " +
                policyList.getPolicyList().toString();
    }

}
