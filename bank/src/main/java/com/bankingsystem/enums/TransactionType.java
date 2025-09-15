// File: src/main/java/com/bankingsystem/enums/TransactionType.java
package com.bankingsystem.enums;

/**
 * Enumeration of transaction types in the banking system.
 * Used for categorization, reporting, and business logic decisions.
 */
public enum TransactionType {
    // Basic transaction types
    DEPOSIT("Deposit", "Money added to account", true, false),
    WITHDRAWAL("Withdrawal", "Money removed from account", false, true),
//    TRANSFER,
    TRANSFER_IN("Transfer In", "Money received from another account", true, false),
    TRANSFER_OUT("Transfer Out", "Money sent to another account", false, true),

    // Fee and interest transactions
//    FEE,
//    INTEREST,
    MONTHLY_FEE("Monthly Fee", "Regular account maintenance fee", false, true),
    OVERDRAFT_FEE("Overdraft Fee", "Fee charged for negative balance", false, true),
    INTEREST_EARNED("Interest Earned", "Interest credited to account", true, false),
    INTEREST_CHARGED("Interest Charged", "Interest debited from account", false, true),


    LOAN_PAYMENT,
    CHECK_PAYMENT,

    ONLINE_PAYMENT,

    // Specialized transactions
    CHECK_DEPOSIT("Check Deposit", "Deposit via check", true, false),
    ATM_WITHDRAWAL("ATM Withdrawal", "Cash withdrawal from ATM", false, true),
    //    WIRE_TRANSFER
    WIRE_TRANSFER_IN("Wire Transfer In", "Incoming wire transfer", true, false),
    WIRE_TRANSFER_OUT("Wire Transfer Out", "Outgoing wire transfer", false, true),
    DIRECT_DEPOSIT("Direct Deposit", "Payroll or government deposit", true, false),
    ACH_DEBIT("ACH Debit", "Automated clearing house debit", false, true),
    ACH_CREDIT("ACH Credit", "Automated clearing house credit", true, false),

    // Adjustment transactions
    ADJUSTMENT_CREDIT("Adjustment Credit", "Manual credit adjustment", true, false),
    ADJUSTMENT_DEBIT("Adjustment Debit", "Manual debit adjustment", false, true),
    REVERSAL("Reversal", "Transaction reversal", false, false);

    private final String displayName;
    private final String description;
    private final boolean increasesBalance;
    private final boolean decreasesBalance;

    TransactionType(String displayName, String description, boolean increasesBalance, boolean decreasesBalance) {
        this.displayName = displayName;
        this.description = description;
        this.increasesBalance = increasesBalance;
        this.decreasesBalance = decreasesBalance;
    }

    // Getter methods
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public boolean increasesBalance() { return increasesBalance; }
    public boolean decreasesBalance() { return decreasesBalance; }

    // Business logic methods
    public boolean isDebitTransaction() {
        return decreasesBalance;
    }

    public boolean isCreditTransaction() {
        return increasesBalance;
    }

    public boolean isFeeTransaction() {
        return this == MONTHLY_FEE || this == OVERDRAFT_FEE;
    }

    public boolean isInterestTransaction() {
        return this == INTEREST_EARNED || this == INTEREST_CHARGED;
    }

    public boolean isTransferTransaction() {
        return this == TRANSFER_IN || this == TRANSFER_OUT ||
                this == WIRE_TRANSFER_IN || this == WIRE_TRANSFER_OUT;
    }

    public boolean requiresApproval() {
        return this == WIRE_TRANSFER_OUT || this == ADJUSTMENT_CREDIT ||
                this == ADJUSTMENT_DEBIT;
    }

    // Static utility methods
    public static TransactionType[] getDepositTypes() {
        return new TransactionType[]{DEPOSIT, CHECK_DEPOSIT, DIRECT_DEPOSIT,
                WIRE_TRANSFER_IN, ACH_CREDIT};
    }

    public static TransactionType[] getWithdrawalTypes() {
        return new TransactionType[]{WITHDRAWAL, ATM_WITHDRAWAL,
                WIRE_TRANSFER_OUT, ACH_DEBIT};
    }

    public static TransactionType fromString(String type) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.name().equalsIgnoreCase(type) ||
                    transactionType.getDisplayName().equalsIgnoreCase(type)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Invalid transaction type: " + type);
    }
}