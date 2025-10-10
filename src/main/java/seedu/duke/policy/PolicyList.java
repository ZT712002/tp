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

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {
        throw new FinanceProPlusException("Not Implemented yet");
    }

    @Override
    public String toString(){
        String result = "";
        for (int i = 0; i < policies.size(); i++) {
            result += (i + 1) + ". " + policies.get(i).toString();
            if (i != policies.size() - 1) {
                result += " ";
            }
        }
        return result;
    }
}
