package seedu.duke.policy;

import seedu.duke.client.Client;
import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Policy {
    private static final String POLICY_REGEX = "\\s+(?=[a-z]+\\/)";
    private String name;
    private String details;

    
    public Policy(String policyNumber) {
        this.name = policyNumber;
        this.details = "";
    }
    
    public Policy(String arguments, boolean parseDetails) throws FinanceProPlusException {
        if (parseDetails) {
            Map<String, String> detailsMap = parsePolicyDetails(arguments);
            List<String> requiredKeys = List.of("n", "d");
            for (String key : requiredKeys) {
                if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                    throw new FinanceProPlusException("Invalid policy details. Please provide all required fields: "
                            + "n/NAME d/DETAILS");
                }
            }
            name = detailsMap.get("n");
            details = detailsMap.get("d");
        } else {
            this.name = arguments;
            this.details = "";
        }
    }
    
    public static Map<String, String> parsePolicyDetails(String policyDetails) {
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = policyDetails.trim();
        String[] parts = trimmedDetails.split(POLICY_REGEX);
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
    
    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        if (details.isEmpty()) {
            return name;
        }
        return "Name: " + name + ", Details: " + details;
    }
}
