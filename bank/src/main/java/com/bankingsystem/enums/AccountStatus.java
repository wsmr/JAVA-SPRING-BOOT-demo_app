// ========================================================================================
// ENUM CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/enums/AccountStatus.java
package com.bankingsystem.enums;

/**
 * Enumeration of possible account statuses.
 * Represents the lifecycle states of a bank account.
 */
public enum AccountStatus {
    ACTIVE("Active", "Account is operational and can perform transactions"),
    INACTIVE("Inactive", "Account exists but cannot perform transactions"),
    FROZEN("Frozen", "Account is temporarily suspended due to security concerns"),
    CLOSED("Closed", "Account has been permanently closed"),
    SUSPENDED("Suspended", "Account is temporarily suspended due to policy violations"),
    PENDING_VERIFICATION("Pending Verification", "Account awaiting identity verification"),
    OVERDRAWN("Overdrawn", "Account balance is negative");

    private final String displayName;
    private final String description;

    // Constructor for enum constants
    AccountStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    // Getter methods
    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    // Business logic methods
    public boolean canPerformTransactions() {
        return this == ACTIVE;
    }

    public boolean isClosedOrSuspended() {
        return this == CLOSED || this == SUSPENDED || this == FROZEN;
    }

    public boolean requiresVerification() {
        return this == PENDING_VERIFICATION;
    }

    // Static utility methods
    public static AccountStatus fromString(String status) {
        for (AccountStatus accountStatus : AccountStatus.values()) {
            if (accountStatus.name().equalsIgnoreCase(status) ||
                    accountStatus.getDisplayName().equalsIgnoreCase(status)) {
                return accountStatus;
            }
        }
        throw new IllegalArgumentException("Invalid account status: " + status);
    }

    // Get all active statuses
    public static AccountStatus[] getActiveStatuses() {
        return new AccountStatus[]{ACTIVE, OVERDRAWN};
    }

    @Override
    public String toString() {
        return displayName;
    }
}