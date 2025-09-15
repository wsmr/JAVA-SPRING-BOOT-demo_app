// ========================================================================================
// UTILITY CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/util/AccountNumberGenerator.java
package com.bankingsystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating unique account numbers.
 * Ensures account numbers are unique, secure, and follow business format.
 */
@Component
public class AccountNumberGenerator {

    private static final String ACCOUNT_PREFIX = "ACC";
    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Generate unique account number
     * @param accountType Type of account (for potential prefix differentiation)
     * @return Unique account number
     */
    public String generateAccountNumber(String accountType) {
        String accountNumber;
        int attempts = 0;
        int maxAttempts = 100;

        do {
            accountNumber = generateAccountNumberInternal(accountType);
            attempts++;

            if (attempts > maxAttempts) {
                throw new RuntimeException("Failed to generate unique account number after " + maxAttempts + " attempts");
            }

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;

        // TODO: Implement account number generation logic
        // TODO: Ensure uniqueness
        // TODO: Add checksum validation
        // TODO: Consider different formats for different account types
        return null;
    }

    /**
     * Validate account number format
     * @param accountNumber Account number to validate
     * @return true if valid format
     */
    public boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }

        // Check format: ACC-XXXXXXXXXX (ACC- followed by 10 digits)
        String pattern = "^" + ACCOUNT_PREFIX + "-\\d{" + ACCOUNT_NUMBER_LENGTH + "}$";
        return accountNumber.matches(pattern);

        // TODO: Implement account number validation
        // TODO: Check format and checksum
        return false;
    }

    /**
     * Extract account type from account number (if encoded)
     * @param accountNumber Account number
     * @return Account type code or null
     */
    public String extractAccountType(String accountNumber) {
        // TODO: Implement account type extraction if encoded in account number
        // This could be useful for account number analytics or routing
        return null;
    }

    /**
     * Generate checksum for account number validation
     * @param accountNumber Account number without checksum
     * @return Checksum digit
     */
    public int generateChecksum(String accountNumber) {
        // TODO: Implement Luhn algorithm or similar for checksum generation
        // This adds an extra layer of validation for account numbers
        return 0;
    }

    // Private helper methods
    private String generateAccountNumberInternal(String accountType) {
        StringBuilder accountNumber = new StringBuilder();

        // Add prefix
        accountNumber.append(ACCOUNT_PREFIX).append("-");

        // Generate random digits
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

    // Alternative generation methods
    private String generateSequentialAccountNumber(String accountType) {
        // TODO: Implement sequential account number generation
        // Useful for testing or when predictable numbers are needed
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return ACCOUNT_PREFIX + "-" + timestamp.substring(timestamp.length() - ACCOUNT_NUMBER_LENGTH);
    }

    private String generateHybridAccountNumber(String accountType) {
        // TODO: Implement hybrid approach (partial sequential, partial random)
        // Combines benefits of both approaches
        String dateComponent = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String randomComponent = String.format("%04d", random.nextInt(10000));
        return ACCOUNT_PREFIX + "-" + dateComponent + randomComponent;
    }
}