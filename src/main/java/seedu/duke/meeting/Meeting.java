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
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for meetings creation cannot be null";
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
        assert this.title != null && !title.isEmpty() : "Title should not be null or empty";
        assert this.client != null && !client.isEmpty() : "Client should not be null or empty";
        assert this.date != null && !date.isEmpty() : "Date should not be null or empty";

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

    public String getTitle() {
        return title;
    }
    public static Map<String, String> parseMeetingDetails(String meetingDetails) {
        assert meetingDetails != null : "Input string for parsing cannot be null";
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
        assert detailsMap != null : "The resulting details map should not be null";
        return detailsMap;
    }


    @Override
    public String toString() {
        assert title != null && !title.isEmpty() : "Title should not be null or empty";
        assert client != null && !client.isEmpty() : "Client should not be null or empty";
        assert date != null && !date.isEmpty() : "Date should not be null or empty";
        String timeInfo = "";
        if (startTime != null && endTime != null) {
            timeInfo = ", Time: " + startTime + " to " + endTime;
        } else if (startTime != null) {
            timeInfo = ", Start Time: " + startTime;
        }
        return "Title: " + title + ", Client: " + client + ", Date: " + date + timeInfo;
    }

    public String toStorageString() {

        StringBuilder sb = new StringBuilder();
        sb.append("t/").append(title)
                .append(" c/").append(client)
                .append(" d/").append(date)
                .append(" from/").append(startTime);
        if (endTime != null && !endTime.isEmpty()) {
            sb.append(" to/").append(endTime);
        }
        return sb.toString();
    }

    public String[] toCSVRow() {

        return new String[]{title, client, date, startTime, endTime == null ? "" : endTime};
    }

}
