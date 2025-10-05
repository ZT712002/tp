package seedu.duke.container;

import seedu.duke.client.ClientList;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;


import java.util.HashMap;

/*
 * A lookup table that maps string keys to their respective list containers.
 * This allows for easy retrieval of different types of lists (e.g., clients, policies, meetings)
 */
public class LookUpTable {
    private HashMap<String, ListContainer> map;
    public LookUpTable(ClientList clients, PolicyList policies, MeetingList meetings) {
        map = new HashMap<String, ListContainer>();
        map.put("client", clients);
        map.put("policy", policies);
        map.put("meeting", meetings);
    }
    public ListContainer getList(String key) {
        return map.get(key);
    }
}
