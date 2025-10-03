package seedu.duke.lookuptable;

import seedu.duke.client.ClientList;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;

import java.util.HashMap;


public class LookUpTable {
    HashMap<String, Class> map;
    public LookUpTable(ClientList clients, PolicyList policies, MeetingList meetings) {
        map = new HashMap<String, Class>();
        map.put("client", clients.getClass());
        map.put("policy", policies.getClass());
        map.put("meeting", meetings.getClass());
    }
}
