package seedu.duke.policy;

public class Policy {
    private String policyNumber;
    public Policy(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public String toString() {
        return policyNumber;
    }
}
