package seedu.duke.policy;

import seedu.duke.exception.FinanceProPlusException;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;


public class ClientPolicy extends Policy {
    private LocalDate startDate;
    private LocalDate expiryDate;
    private BigDecimal monthlyPremium;

    public ClientPolicy(Policy basePolicy, LocalDate startDate, LocalDate expiryDate, BigDecimal monthlyPremium)
            throws FinanceProPlusException {
        super(basePolicy.getName(), false);
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.monthlyPremium = monthlyPremium;
    }

    public ClientPolicy(Policy basePolicy) throws FinanceProPlusException {
        super(basePolicy.getName(), false);
        this.startDate = null;
        this.expiryDate = null;
        this.monthlyPremium = null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public BigDecimal getMonthlyPremium() {
        return monthlyPremium;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setMonthlyPremium(BigDecimal monthlyPremium) {
        this.monthlyPremium = monthlyPremium;
    }

    @Override
    public String toString() {
        String premiumStr = (monthlyPremium != null) ? "$" + monthlyPremium : "Not set";
        String startStr = (startDate != null) ? startDate.toString() : "Not set";
        String expiryStr = (expiryDate != null) ? expiryDate.toString() : "Not set";
        return super.toString() + " [Premium: " + premiumStr
                + ", Starts: " + startStr
                + ", Expires: " + expiryStr + "]";
    }

    public String viewDetails(){
        StringBuilder sb = new StringBuilder();
        sb.append("Policy Name: ").append(super.getName());
        String premiumStr;
        if (this.monthlyPremium != null) {
            premiumStr = String.format("$%.2f", this.monthlyPremium); // Formats to 2 decimal places
        } else {
            premiumStr = "Not set";
        }
        sb.append("\n    - Monthly Premium: ").append(premiumStr);
        String startStr;
        if (this.startDate != null) {
            startStr = this.startDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); // e.g., "14 Sep 1999"
        } else {
            startStr = "Not set";
        }
        sb.append("\n    - Start Date: ").append(startStr);
        String expiryStr;
        if (this.expiryDate != null) {
            expiryStr = this.expiryDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } else {
            expiryStr = "Not set";
        }
        sb.append("\n    - Expiry Date: ").append(expiryStr);
        return sb.toString();
    }
}
