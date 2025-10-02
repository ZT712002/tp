package seedu.duke.policy;

import java.util.ArrayList;

public class PolicyList {
    private ArrayList<Policy> policies = new ArrayList<Policy>();
    public void addPolicy(Policy policy) {
        policies.add(policy);
    }
    public ArrayList<Policy> getPolicies() {
        return policies;
    }
}
