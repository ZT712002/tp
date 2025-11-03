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
        assert arguments != null && !arguments.trim().isEmpty()
                : "Arguments for user creation cannot be null or empty";

        Map<String, String> detailsMap = parseUserDetails(arguments);
        assert detailsMap != null : "Parsed user details map should not be null after parsing";
        List<String> requiredKeys = List.of("n", "e", "c", "r");

        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid user details. Please provide all required fields: "
                        + "n/NAME e/EMAIL c/CONTACT r/REPRESENTATIVE_NUMBER");
            }
        }

        name = detailsMap.get("n");
        email = detailsMap.get("e");
        representativeNumber = detailsMap.get("r");

        try {
            phoneNumber = Integer.parseInt(detailsMap.get("c"));
        } catch (NumberFormatException ex) {
            throw new FinanceProPlusException("Invalid contact format. Please enter digits only.");
        }

        validateEmail(email);

        assert this.name != null && !this.name.isEmpty()
                : "User name should be initialized";
        assert this.email != null && !this.email.isEmpty()
                : "User email should be initialized";
        assert this.representativeNumber != null && !this.representativeNumber.isEmpty()
                : "User representative number should be initialized";
    }

    /**
     * Parses user details into a map keyed by short codes ({n}, {e}, {c}, {r}).
     * Unknown tokens are ignored.
     *
     * @param userDetails raw argument string.
     * @return map of parsed keys to values; empty if input is null/blank.
     */
    public static Map<String, String> parseUserDetails(String userDetails) {
        assert userDetails != null : "Input string for parsing cannot be null";

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
        assert detailsMap != null : "The resulting details map should not be null";
        return detailsMap;
    }

    /**
     * Returns a human readable multi-line representation of the user.
     *
     * @return formatted string containing name, email, contact, and representative number.
     */
    @Override
    public String toString() {
        assert name != null : "Name should not be null when calling toString";
        assert email != null : "Email should not be null when calling toString";
        assert representativeNumber != null : "Representative number should not be null when calling toString";
        return "User Details:\n"
                + "Name: " + name + "\n"
                + "Email: " + email + "\n"
                + "Contact: " + phoneNumber
                + "\n" + "Representative No.: " + representativeNumber;
    }

    /**
     * Returns a single-line storage representation of this user
     * (e.g., {n/John e/j@x.com c/98765432 r/FA-12345}).
     *
     * @return storage line containing all fields.
     */
    String toStorageString() {
        return String.format("n/%s e/%s c/%d r/%s", name, email, phoneNumber, representativeNumber);
    }

    public String[] toCSVRow() {
        return new String[]{name, email, String.valueOf(phoneNumber), representativeNumber};
    }
    /**
     * Validates the email format using a simple pattern.
     *
     * @param email email to validate.
     * @throws FinanceProPlusException if the email is invalid.
     */
    private void validateEmail(String email) throws FinanceProPlusException {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new FinanceProPlusException("Invalid email format." +
                    "Please provide a valid email address (e.g., alex@example.com).");

        }
    }
}
