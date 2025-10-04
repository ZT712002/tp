package seedu.duke.policy;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;

public class PolicyList implements ListContainer {
    private ArrayList<Policy> policies = new ArrayList<Policy>();
    public void addPolicy(Policy policy) {
        policies.add(policy);
    }
    public ArrayList<Policy> getPolicyList() {
        return policies;
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {

    }
}
