package seedu.duke.user;

import seedu.duke.exception.FinanceProPlusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private static final String USER_REGEX = "\\s+(?=[a-z]+/)";
    private String name;
    private String email;
    private int phoneNumber;
    private String representativeNumber;

    public User(String arguments) throws FinanceProPlusException {
        Map<String, String> detailsMap = parseUserDetails(arguments);

        List<String> requiredKeys = List.of("n", "e", "p", "r");

        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid user details. Please provide all required fields: "
                        + "n/NAME e/EMAIL p/PHONE r/REPRESENTATIVE_NUMBER");
            }
        }

        name = detailsMap.get("n");
        email = detailsMap.get("e");
        representativeNumber = detailsMap.get("r");

        try {
            phoneNumber = Integer.parseInt(detailsMap.get("p"));
        } catch (NumberFormatException ex) {
            throw new FinanceProPlusException("Invalid phone number format. Please enter digits only.");
        }
    }

    public static Map<String, String> parseUserDetails(String userDetails) {
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = userDetails.trim();
        String[] parts = trimmedDetails.split(USER_REGEX);

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

    @Override
    public String toString() {
        return "User Details:\n"
                + "Name: " + name + "\n"
                + "Email: " + email + "\n"
                + "Phone: " + phoneNumber
                + "\n" + "Representative No.: " + representativeNumber;
    }
}
