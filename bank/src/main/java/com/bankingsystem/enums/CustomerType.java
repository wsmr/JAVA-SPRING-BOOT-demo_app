// File: src/main/java/com/bankingsystem/enums/CustomerType.java
package com.bankingsystem.enums;

public enum CustomerType {
    BASIC("Basic Customer"),
    PREMIUM("Premium Customer"),
    VIP("VIP Customer"),
    BUSINESS("Business Customer");

    private final String displayName;

    CustomerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}