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
        
        assert basePolicy != null : "Base policy cannot be null";
        assert startDate != null : "Start date cannot be null";
        assert expiryDate != null : "Expiry date cannot be null";
        assert monthlyPremium != null : "Monthly premium cannot be null";
        assert monthlyPremium.compareTo(BigDecimal.ZERO) >= 0 : "Monthly premium must be non-negative";
        assert !startDate.isAfter(expiryDate) : "Start date must be before or equal to expiry date";
        
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.monthlyPremium = monthlyPremium;
        
        assert this.startDate != null : "Start date was not set properly";
        assert this.expiryDate != null : "Expiry date was not set properly";
        assert this.monthlyPremium != null : "Monthly premium was not set properly";
    }

    public ClientPolicy(Policy basePolicy) throws FinanceProPlusException {
        super(basePolicy.getName(), false);
        
        assert basePolicy != null : "Base policy cannot be null";
        
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
        assert startDate != null : "Start date cannot be null";
        if (this.expiryDate != null) {
            assert !startDate.isAfter(this.expiryDate) : "Start date must be before or equal to expiry date";
        }
        this.startDate = startDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        assert expiryDate != null : "Expiry date cannot be null";
        if (this.startDate != null) {
            assert !this.startDate.isAfter(expiryDate) : "Expiry date must be after or equal to start date";
        }
        this.expiryDate = expiryDate;
    }

    public void setMonthlyPremium(BigDecimal monthlyPremium) {
        assert monthlyPremium != null : "Monthly premium cannot be null";
        assert monthlyPremium.compareTo(BigDecimal.ZERO) >= 0 : "Monthly premium must be non-negative";
        this.monthlyPremium = monthlyPremium;
    }

    @Override
    public String toString() {
        String premiumStr = (monthlyPremium != null) ? "$" + monthlyPremium : "Not set";
        String startStr = (startDate != null) ? startDate.toString() : "Not set";
        String expiryStr = (expiryDate != null) ? expiryDate.toString() : "Not set";
        return "Policy: "+ super.toString() + " [Premium: " + premiumStr
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
