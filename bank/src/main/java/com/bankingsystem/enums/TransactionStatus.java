// File: src/main/java/com/bankingsystem/enums/TransactionStatus.java
package com.bankingsystem.enums;

public enum TransactionStatus {
    PENDING("Pending Processing"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    CANCELLED("Cancelled"),
    REVERSED("Reversed");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}