package seedu.duke.policy;

import seedu.duke.container.ListContainer;

import java.util.ArrayList;

public class PolicyList implements ListContainer {
    private ArrayList<Policy> policies = new ArrayList<Policy>();
    public void addPolicy(Policy policy) {
        policies.add(policy);
    }
    public ArrayList<Policy> getPolicies() {
        return policies;
    }
}
