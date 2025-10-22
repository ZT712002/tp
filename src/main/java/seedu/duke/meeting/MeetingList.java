package seedu.duke.meeting;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;
import java.util.logging.Logger;


public class MeetingList implements ListContainer {
    private static final Logger logger = Logger.getLogger(MeetingList.class.getName());
    private ArrayList<Meeting> meetings;

    public MeetingList() {
        this.meetings = new ArrayList<>();
        assert meetings != null : "MeetingList should not be null after initialization";
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        Meeting meeting = new Meeting(arguments);
        assert meeting != null : "Meeting to be added should not be null";
        assert meetings != null : "MeetingList should not be null when adding an item";
        int oldSize = meetings.size();
        meetings.add(meeting);
        assert meetings.size() == oldSize + 1: "MeetingList size should increase by 1 after adding an item";
        System.out.println("Noted. I've added this meeting:");
        System.out.println(meeting.toString());
        logger.info("Successfully added new meeting: " + meeting.getTitle());
    }

    @Override
    public void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException {
        throw new FinanceProPlusException("Implemented only on ClientList class");
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if (meetings.size() == 0) {
            System.out.println("No meetings to delete.");
            return;
        }
        int oldSize = meetings.size();
        int index = checkDeleteIndex(arguments);
        Meeting removedMeeting = meetings.remove(index);
        assert meetings.size() == oldSize - 1 : "Meeting list size should decrease by 1 after deleting a meeting";
        System.out.println("Noted. I've removed this meeting:");
        System.out.println(removedMeeting.toString());
        logger.info("Successfully deleted meeting: " + removedMeeting.getTitle());
    }

    @Override
    public void listItems() throws FinanceProPlusException {
        if (meetings.size() == 0) {
            System.out.println("No meetings found.");
        } else {
            System.out.println("Here are the meetings in your list:");
            for (int i = 0; i < meetings.size(); i++) {
                System.out.println((i + 1) + ". " + meetings.get(i).toString());
            }
        }
    }

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {
        int index;
        try {
            index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= meetings.size()) {
                throw new FinanceProPlusException("Invalid index. Please provide a valid meeting index to delete.");
            }
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid input. Please provide a valid meeting index to delete.");
        }
        logger.fine("Validated delete index: " + index);
        return index;
    }
}
