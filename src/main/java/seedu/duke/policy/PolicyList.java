package seedu.duke.policy;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;

public class PolicyList implements ListContainer {
    private ArrayList<Policy> policies = new ArrayList<Policy>();
    public void addPolicy(Policy policy) {
        assert policy != null : "Policy to add cannot be null";
        assert policies != null : "Policies list must be initialized";
        
        int sizeBefore = policies.size();
        policies.add(policy);
        
        assert policies.size() == sizeBefore + 1 : "Policy was not added correctly";
    }
    public ArrayList<Policy> getPolicyList() {
        return policies;
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        assert arguments != null : "Arguments cannot be null";
        assert !arguments.trim().isEmpty() : "Arguments cannot be empty";
        
        Policy policy = new Policy(arguments, true);
        assert policy != null : "Created policy cannot be null";
        
        addPolicy(policy);
        System.out.println("Noted. I've added this policy:");
        System.out.println(policy.toString());
    }

    @Override
    public void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException {
        throw new FinanceProPlusException("Implemented only on ClientList class");
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        assert arguments != null : "Arguments cannot be null";
        assert policies != null : "Policies list must be initialized";
        
        if(policies.size() == 0) {
            System.out.println("No policies to delete.");
            return;
        }
        int index = checkDeleteIndex(arguments);
        assert index >= 0 && index < policies.size() : "Index must be within valid range";
        
        int sizeBefore = policies.size();
        Policy removedPolicy = policies.remove(index);
        
        assert removedPolicy != null : "Removed policy cannot be null";
        assert policies.size() == sizeBefore - 1 : "Policy was not removed correctly";
        
        System.out.println("Noted. I've removed this policy:");
        System.out.println(removedPolicy.toString());
    }

    @Override
    public void listItems() throws FinanceProPlusException {
        assert policies != null : "Policies list must be initialized";
        
        if (policies.size() == 0) {
            System.out.println("No policies found.");
        } else {
            System.out.println("Here are the policies in your list:");
            for (int i = 0; i < policies.size(); i++) {
                assert policies.get(i) != null : "Policy at index " + i + " cannot be null";
                System.out.println((i + 1) + ". " + policies.get(i).toString());
            }
        }
    }

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {
        assert arguments != null : "Arguments cannot be null";
        assert policies != null : "Policies list must be initialized";
        
        int index;
        try {
            index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= policies.size()) {
                throw new FinanceProPlusException("Invalid index. Please provide a valid policy index to delete.");
            }
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid input. Please provide a valid policy index to delete.");
        }
        
        assert index >= 0 && index < policies.size() : "Returned index must be within valid range";
        return index;
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
    public Policy findPolicyByName(String policyName) throws FinanceProPlusException {
        assert policies != null : "Policies list must be initialized";
        
        if (policyName == null || policyName.isEmpty()) {
            throw new FinanceProPlusException("Internal Error: Policy name to find cannot be null or empty.");
        }
        for (Policy policy : this.policies) {
            assert policy != null : "Policy in list cannot be null";
            assert policy.getName() != null : "Policy name cannot be null";
            if (policy.getName().equals(policyName)) {
                return policy;
            }
        }
        return null;
    }
}
