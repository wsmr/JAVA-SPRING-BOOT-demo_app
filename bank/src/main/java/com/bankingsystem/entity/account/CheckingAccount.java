// File: src/main/java/com/bankingsystem/entity/account/CheckingAccount.java
package com.bankingsystem.entity.account;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "checking_accounts")
@DiscriminatorValue("CHECKING")
public class CheckingAccount extends Account {

    @Column(name = "overdraft_limit")
    private Double overdraftLimit;

    @Column(name = "monthly_fee")
    private Double monthlyFee;

    @Column(name = "free_transactions_limit")
    private Integer freeTransactionsLimit;

    // TODO: Override withdraw() method with overdraft handling
    // TODO: Implement issueCheck() method
    // TODO: Implement calculateOverdraftFee() method
    // TODO: Implement chargeMonthlyFee() method
    // TODO: Override close() method
    // TODO: Override validateTransaction() method
    // TODO: Implement all getter and setter methods
}