package seedu.duke.meeting;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;

public class MeetingList implements ListContainer {
    private ArrayList<Meeting> meetings = new ArrayList<Meeting>();
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        throw new FinanceProPlusException("Not Implemented yet");
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        throw new FinanceProPlusException("Not Implemented yet");
    }

    @Override
    public void listItems() throws FinanceProPlusException {
        throw new FinanceProPlusException("Not Implemented yet");
    }
}
