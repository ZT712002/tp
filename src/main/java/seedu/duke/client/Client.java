package seedu.duke.client;

import seedu.duke.policy.PolicyList;

public class Client {
    private String name;
    private PolicyList policyList;
    private int PhoneNumber;
    public Client(String name, int PhoneNumber) {
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.policyList = new PolicyList();
    }
    public String getName() {
        return name;
    }
    public int getPhoneNumber() {
        return PhoneNumber;
    }
    public PolicyList getPolicyList() {
        return policyList;
    }
}
