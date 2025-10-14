package seedu.duke.meeting;

import seedu.duke.exception.FinanceProPlusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meeting {
    private static final String MEETING_REGEX = "\\s+(?=[a-z]+/)";
    private String title;
    private String date;
    private String client;
    private String startTime;
    private String endTime;

    public Meeting(String arguments) throws FinanceProPlusException {
        Map<String, String> detailsMap = parseMeetingDetails(arguments);
        List<String> requiredKeys = List.of("t", "c", "d", "from");
        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid meeting details. Please provide all required fields: "
                        + "t/TITLE c/CLIENT d/DATE from/START_TIME");
            }
        }
        title = detailsMap.get("t");
        date = detailsMap.get("d");
        client = detailsMap.get("c");
        startTime = detailsMap.get("from");
        endTime = detailsMap.get("to");

        validateDateFormat(date);
        validateTimeFormat(startTime);
        if (endTime != null) {
            validateTimeFormat(endTime);
        }
    }

    private void validateDateFormat(String dateString) throws FinanceProPlusException {
        if (!dateString.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new FinanceProPlusException("Invalid date format. Please use dd-MM-yyyy (e.g., 15-01-2024)");
        }
    }

    private void validateTimeFormat(String timeString) throws FinanceProPlusException {
        if (!timeString.matches("\\d{2}:\\d{2}")) {
            throw new FinanceProPlusException("Invalid time format. Please use HH:MM (e.g., 14:30)");
        }
    }

    public static Map<String, String> parseMeetingDetails(String meetingDetails) {
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = meetingDetails.trim();
        String[] parts = trimmedDetails.split(MEETING_REGEX);
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
        String timeInfo = "";
        if (startTime != null && endTime != null) {
            timeInfo = ", Time: " + startTime + " to " + endTime;
        } else if (startTime != null) {
            timeInfo = ", Start Time: " + startTime;
        }
        return "Title: " + title + ", Client: " + client + ", Date: " + date + timeInfo;
    }
}
