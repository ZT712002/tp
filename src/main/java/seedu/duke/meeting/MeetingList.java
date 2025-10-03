package seedu.duke.meeting;

import seedu.duke.container.ListContainer;

import java.util.ArrayList;

public class MeetingList implements ListContainer {
    private ArrayList<Meeting> meetings = new ArrayList<Meeting>();
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }
}
