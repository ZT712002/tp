package seedu.duke.container;

import seedu.duke.client.ClientList;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;
import seedu.duke.user.UserList;

import java.util.HashMap;

/*
 * A lookup table that maps string keys to their respective list containers.
 * This allows for easy retrieval of different types of lists (e.g., clients, policies, meetings, tasks)
 */
public class LookUpTable {
    private HashMap<String, ListContainer> map;
    public LookUpTable(ClientList clients, PolicyList policies, MeetingList meetings, TaskList tasks, UserList user) {
        map = new HashMap<String, ListContainer>();
        map.put("client", clients);
        map.put("policy", policies);
        map.put("meeting", meetings);
        map.put("task", tasks);
        map.put("user", user);
    }
    public ListContainer getList(String key) {
        return map.get(key);
    }
}
