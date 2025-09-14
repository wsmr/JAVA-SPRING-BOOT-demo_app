// ========================================================================================
// ENUM CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/enums/AccountStatus.java
package com.bankingsystem.enums;

public enum AccountStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    FROZEN("Frozen"),
    CLOSED("Closed"),
    SUSPENDED("Suspended");

    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}