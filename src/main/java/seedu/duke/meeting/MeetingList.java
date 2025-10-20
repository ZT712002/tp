package seedu.duke.meeting;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;

public class MeetingList implements ListContainer {
    private ArrayList<Meeting> meetings;

    public MeetingList() {
        this.meetings = new ArrayList<>();
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        Meeting meeting = new Meeting(arguments);
        meetings.add(meeting);
        System.out.println("Noted. I've added this meeting:");
        System.out.println(meeting.toString());
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
        int index = checkDeleteIndex(arguments);
        Meeting removedMeeting = meetings.remove(index);
        System.out.println("Noted. I've removed this meeting:");
        System.out.println(removedMeeting.toString());
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
        return index;
    }
}
