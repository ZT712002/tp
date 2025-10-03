package seedu.duke.meeting;

import java.util.ArrayList;

public class MeetingList {
    private ArrayList<Meeting> meetings = new ArrayList<Meeting>();
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }
}
