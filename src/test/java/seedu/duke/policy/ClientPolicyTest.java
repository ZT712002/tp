package seedu.duke.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceProPlusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class ClientPolicyTest {

    private Policy basePolicy;

    @BeforeEach
    void setUp() throws FinanceProPlusException {
        
        basePolicy = new Policy("n/1234 d/Health Test Policy", true);
    }

    @Test
    void constructor_fullDetails_setsAllFieldsCorrectly() throws FinanceProPlusException {
        
        LocalDate startDate = LocalDate.of(2023, 1, 15);
        LocalDate expiryDate = LocalDate.of(2025, 1, 14);
        BigDecimal premium = new BigDecimal("150.75");
        ClientPolicy clientPolicy = new ClientPolicy(basePolicy, startDate, expiryDate, premium);
        assertNotNull(clientPolicy);
        assertEquals("1234", clientPolicy.getName()); // Inherited from base policy
        assertEquals(startDate, clientPolicy.getStartDate());
        assertEquals(expiryDate, clientPolicy.getExpiryDate());
        assertEquals(0, premium.compareTo(clientPolicy.getMonthlyPremium())); // Use compareTo for BigDecimal
    }

    @Test
    void constructor_placeholder_setsNameAndNulls() throws FinanceProPlusException {
        ClientPolicy placeholderPolicy = new ClientPolicy(basePolicy);
        assertNotNull(placeholderPolicy);
        assertEquals("1234", placeholderPolicy.getName());
        assertNull(placeholderPolicy.getStartDate());
        assertNull(placeholderPolicy.getExpiryDate());
        assertNull(placeholderPolicy.getMonthlyPremium());
    }
    @Test
    void setters_updateFieldsCorrectly() throws FinanceProPlusException {
        // Arrange
        ClientPolicy policy = new ClientPolicy(basePolicy); // Start with a placeholder
        LocalDate newStartDate = LocalDate.of(2024, 2, 1);
        LocalDate newExpiryDate = LocalDate.of(2026, 2, 1);
        BigDecimal newPremium = new BigDecimal("99.99");
        policy.setStartDate(newStartDate);
        policy.setExpiryDate(newExpiryDate);
        policy.setMonthlyPremium(newPremium);
        assertEquals(newStartDate, policy.getStartDate());
        assertEquals(newExpiryDate, policy.getExpiryDate());
        assertEquals(newPremium, policy.getMonthlyPremium());
    }
    @Test
    void toString_fullDetails_returnsCorrectFormattedString() throws FinanceProPlusException {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate expiryDate = LocalDate.of(2024, 12, 31);
        BigDecimal premium = new BigDecimal("200.00");
        ClientPolicy policy = new ClientPolicy(basePolicy, startDate, expiryDate, premium);
        String expected = "Policy: 1234 [Premium: $200.00, Starts: 2023-01-01, Expires: 2024-12-31]";
        String result = policy.toString();
        assertEquals(expected, result);
    }

    @Test
    void toString_placeholderDetails_returnsStringWithNotSet() throws FinanceProPlusException {
        ClientPolicy policy = new ClientPolicy(basePolicy);
        String expected = "Policy: 1234 [Premium: Not set, Starts: Not set, Expires: Not set]";
        String result = policy.toString();
        assertEquals(expected, result);
    }
    
    @Test
    void viewDetails_fullDetails_returnsCorrectMultiLineFormattedString() throws FinanceProPlusException {
        LocalDate startDate = LocalDate.of(1999, 9, 14);
        LocalDate expiryDate = LocalDate.of(2030, 1, 1);
        BigDecimal premium = new BigDecimal("123.456"); 
        ClientPolicy policy = new ClientPolicy(basePolicy, startDate, expiryDate, premium);
        String expected = "Policy Name: 1234\n" +
                "    - Monthly Premium: $123.46\n" +
                "    - Start Date: 14 Sep 1999\n" +
                "    - Expiry Date: 01 Jan 2030";
        String result = policy.viewDetails();
        assertEquals(expected, result);
    }

    @Test
    void viewDetails_placeholderDetails_returnsMultiLineStringWithNotSet() throws FinanceProPlusException {
        // Arrange
        ClientPolicy policy = new ClientPolicy(basePolicy);
        String expected = "Policy Name: 1234\n" +
                "    - Monthly Premium: Not set\n" +
                "    - Start Date: Not set\n" +
                "    - Expiry Date: Not set";
        String result = policy.viewDetails();
        assertEquals(expected, result);
    }
    
}