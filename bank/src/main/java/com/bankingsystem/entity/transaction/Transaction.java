// ========================================================================================
// TRANSACTION HIERARCHY
// ========================================================================================

// File: src/main/java/com/bankingsystem/entity/transaction/Transaction.java
package com.bankingsystem.entity.transaction;

import com.bankingsystem.entity.base.AuditableEntity;
import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.BankEmployee;
import com.bankingsystem.enums.TransactionType;
import com.bankingsystem.enums.TransactionStatus;
import com.bankingsystem.enums.Currency;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "transaction_type")
public abstract class Transaction extends AuditableEntity {

    @Column(name = "transaction_id", unique = true, nullable = false)
    protected String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    protected TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    protected Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    protected Currency currency;

    @Column(name = "timestamp")
    protected LocalDateTime timestamp;

    @Column(name = "description")
    protected String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    protected TransactionStatus status;

    @Column(name = "fees")
    protected Double fees;

    @Column(name = "authorization_code")
    protected String authorizationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    protected Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    protected BankEmployee processedBy;

    // TODO: Implement abstract execute() method
    // TODO: Implement abstract undo() method
    // TODO: Implement abstract validate() method
    // TODO: Implement abstract calculateFees() method
    // TODO: Implement getAuditTrail() method
    // TODO: Implement all getter and setter methods
}
