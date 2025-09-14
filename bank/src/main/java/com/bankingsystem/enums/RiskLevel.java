// File: src/main/java/com/bankingsystem/enums/RiskLevel.java
package com.bankingsystem.enums;

public enum RiskLevel {
    LOW("Low Risk"),
    MEDIUM("Medium Risk"),
    HIGH("High Risk"),
    CRITICAL("Critical Risk");

    private final String displayName;

    RiskLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}