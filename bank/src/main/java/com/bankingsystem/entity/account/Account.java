// ========================================================================================
// ACCOUNT HIERARCHY
// ========================================================================================

// File: src/main/java/com/bankingsystem/entity/account/Account.java
package com.bankingsystem.entity.account;

import com.bankingsystem.entity.base.AuditableEntity;
import com.bankingsystem.entity.person.Customer;
import com.bankingsystem.entity.transaction.Transaction;
import com.bankingsystem.enums.AccountStatus;
import com.bankingsystem.enums.Currency;
import com.bankingsystem.service.interfaces.IInterestCalculator;
import com.bankingsystem.service.interfaces.IFeeCalculator;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "account_type")
public abstract class Account extends AuditableEntity {

    @Column(name = "account_id", unique = true, nullable = false)
    protected String accountId;

    @Column(name = "account_number", unique = true, nullable = false)
    protected String accountNumber;

    @Column(name = "balance", nullable = false)
    protected Double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    protected Currency currency;

    @Column(name = "open_date")
    protected LocalDate openDate;

    @Column(name = "last_transaction_date")
    protected LocalDateTime lastTransactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    protected AccountStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    protected Customer owner;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Transaction> transactionHistory;

    @Transient
    protected IInterestCalculator interestCalculator;

    @Transient
    protected IFeeCalculator feeCalculator;

    // TODO: Implement deposit() method with validation
    // TODO: Implement abstract withdraw() method
    // TODO: Implement transfer() method
    // TODO: Implement getBalance() method
    // TODO: Implement getTransactionHistory() method with date range
    // TODO: Implement calculateInterest() method
    // TODO: Implement applyFees() method
    // TODO: Implement freeze() and unfreeze() methods
    // TODO: Implement abstract close() method
    // TODO: Implement abstract validateTransaction() method
    // TODO: Implement all getter and setter methods
}