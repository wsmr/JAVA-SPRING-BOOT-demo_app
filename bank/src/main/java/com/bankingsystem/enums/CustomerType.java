// File: src/main/java/com/bankingsystem/enums/CustomerType.java
package com.bankingsystem.enums;

import java.math.BigDecimal;

/**
 * Customer tier levels with associated benefits and limits.
 * Used for business logic decisions and personalized services.
 */
public enum CustomerType {
    BASIC("Basic Customer", 0, new BigDecimal("1000"), new BigDecimal("500"), 0),
    PREMIUM("Premium Customer", 5000, new BigDecimal("5000"), new BigDecimal("2000"), 1),
    VIP("VIP Customer", 25000, new BigDecimal("25000"), new BigDecimal("10000"), 3),
    BUSINESS("Business Customer", 0, new BigDecimal("100000"), new BigDecimal("50000"), 5);

    private final String displayName;
    private final int minimumBalance;
    private final BigDecimal dailyWithdrawalLimit;
    private final BigDecimal dailyTransferLimit;
    private final int freeTransactionsPerMonth;

    CustomerType(String displayName, int minimumBalance,
                 BigDecimal dailyWithdrawalLimit, BigDecimal dailyTransferLimit,
                 int freeTransactionsPerMonth) {
        this.displayName = displayName;
        this.minimumBalance = minimumBalance;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.dailyTransferLimit = dailyTransferLimit;
        this.freeTransactionsPerMonth = freeTransactionsPerMonth;
    }

    // Getter methods
    public String getDisplayName() { return displayName; }
    public int getMinimumBalance() { return minimumBalance; }
    public BigDecimal getDailyWithdrawalLimit() { return dailyWithdrawalLimit; }
    public BigDecimal getDailyTransferLimit() { return dailyTransferLimit; }
    public int getFreeTransactionsPerMonth() { return freeTransactionsPerMonth; }

    // Business logic methods
    public boolean hasWithdrawalFees() {
        return this == BASIC;
    }

    public boolean hasMonthlyFees() {
        return this == BASIC && minimumBalance < 1000;
    }

    public BigDecimal getOverdraftLimit() {
        switch (this) {
            case BASIC: return BigDecimal.ZERO;
            case PREMIUM: return new BigDecimal("500");
            case VIP: return new BigDecimal("2000");
            case BUSINESS: return new BigDecimal("5000");
            default: return BigDecimal.ZERO;
        }
    }

    public double getInterestRateBonus() {
        switch (this) {
            case BASIC: return 0.0;
            case PREMIUM: return 0.0025; // +0.25%
            case VIP: return 0.005;      // +0.5%
            case BUSINESS: return 0.0;
            default: return 0.0;
        }
    }

    // Upgrade/downgrade logic
    public CustomerType getNextTier() {
        switch (this) {
            case BASIC: return PREMIUM;
            case PREMIUM: return VIP;
            case VIP: return VIP; // Already at top
            case BUSINESS: return BUSINESS; // Different track
            default: return this;
        }
    }

    public boolean canUpgradeTo(CustomerType targetType) {
        if (this == BUSINESS || targetType == BUSINESS) {
            return false; // Business is separate track
        }
        return targetType.ordinal() > this.ordinal();
    }

    // Static utility methods
    public static CustomerType determineTypeByBalance(BigDecimal totalBalance) {
        if (totalBalance.compareTo(new BigDecimal("25000")) >= 0) {
            return VIP;
        } else if (totalBalance.compareTo(new BigDecimal("5000")) >= 0) {
            return PREMIUM;
        } else {
            return BASIC;
        }
    }
}