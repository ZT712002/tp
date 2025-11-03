package seedu.duke.policy;

import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    /**
     * Deletes a base policy and cascades the delete to all clients who have that policy.
     *
     * @param arguments The index of the policy to delete.
     * @param clientList The list of all clients to check for cascading deletes.
     * @throws FinanceProPlusException If the index is invalid.
     */
    public void deleteItem(String arguments, ClientList clientList) throws FinanceProPlusException {
        assert arguments != null : "Arguments cannot be null";
        assert policies != null : "Policies list must be initialized";
        assert clientList != null : "Client list cannot be null for a cascading delete";
        if(policies.isEmpty()) {
            System.out.println("No policies to delete.");
            return;
        }
        int index = checkDeleteIndex(arguments);
        assert index >= 0 && index < policies.size() : "Index must be within valid range";
        Policy removedPolicy = policies.get(index);
        String removedPolicyName = removedPolicy.getName();
        policies.remove(index);
        System.out.println("Noted. I've removed this base policy:");
        System.out.println(removedPolicy.toString());
        System.out.println("----------------------------------------------------");
        System.out.println("Checking clients for associated policy contracts...");
        int removalCount = 0;
        for (Client client : clientList.getClientList()) {
            // Ask the client to remove any policy with this name
            boolean wasRemoved = client.removePolicyByName(removedPolicyName);
            if (wasRemoved) {
                System.out.println("- Removed contract for '" + removedPolicyName + "' from client: "
                        + client.getName());
                removalCount++;
            }
        }
        if (removalCount > 0) {
            System.out.println("Successfully removed " + removalCount + " associated policy contract(s) from clients.");
        } else {
            System.out.println("No clients had a contract for the deleted policy.");
        }
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
            String lowerCaseName = policy.getName().toLowerCase();
            if (lowerCaseName.equals(policyName.toLowerCase())) {
                return policy;
            }
        }
        return null;
    }

    public List<String> toStorageFormat() {
        List<String> lines = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Policy p : policies) {
            if (p instanceof ClientPolicy) {
                ClientPolicy cp = (ClientPolicy) p;
                lines.add(String.format(
                        "p/%s m/%.2f s/%s e/%s",
                        cp.getName(),
                        cp.getMonthlyPremium(),
                        cp.getStartDate().format(formatter),
                        cp.getExpiryDate().format(formatter)
                ));
            } else {
                lines.add(p.toStorageString());
            }
        }
        return lines;
    }


    public void loadFromStorage(List<String> lines) throws FinanceProPlusException {
        for (String line : lines) {

            policies.add(new Policy(line, true));
        }
    }

    public List<String[]> toCSVFormat() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Name", "Details"});
        for (Policy p : policies) {
            rows.add(p.toCSVRow());
        }
        return rows;
    }


}
