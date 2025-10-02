package seedu.duke.policy;

public class Policy {
    private String policyName;
    private String policyNumber;
    private String policyType;
    private double premiumAmount;

    public Policy(String policyName, String policyNumber, String policyType, double premiumAmount) {
        this.policyName = policyName;
        this.policyNumber = policyNumber;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
    }

    public String getPolicyName() {
        return policyName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyType() {
        return policyType;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyName='" + policyName + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", policyType='" + policyType + '\'' +
                ", premiumAmount=" + premiumAmount +
                '}';
    }
}
