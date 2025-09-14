// File: src/main/java/com/bankingsystem/entity/account/SavingsAccount.java
package com.bankingsystem.entity.account;

import jakarta.persistence.*;

@Entity
@Table(name = "savings_accounts")
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "minimum_balance")
    private Double minimumBalance;

    @Column(name = "withdrawal_limit")
    private Integer withdrawalLimit;

    @Column(name = "withdrawals_this_month")
    private Integer withdrawalsThisMonth;

    // TODO: Override withdraw() method with withdrawal limit check
    // TODO: Override calculateInterest() method
    // TODO: Implement applyInterest() method
    // TODO: Implement checkWithdrawalLimit() method
    // TODO: Override close() method with minimum balance check
    // TODO: Override validateTransaction() method
    // TODO: Implement all getter and setter methods
}
