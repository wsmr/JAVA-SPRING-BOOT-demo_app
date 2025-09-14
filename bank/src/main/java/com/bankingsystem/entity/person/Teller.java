// File: src/main/java/com/bankingsystem/entity/person/Teller.java
package com.bankingsystem.entity.person;

import com.bankingsystem.entity.transaction.Transaction;
import com.bankingsystem.entity.account.Account;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tellers")
public class Teller extends BankEmployee {

    @Column(name = "window_number")
    private Integer windowNumber;

    @Column(name = "daily_transaction_limit")
    private Double dailyTransactionLimit;

    @OneToMany(mappedBy = "processedBy", fetch = FetchType.LAZY)
    private List<Transaction> transactionsProcessed;

    // TODO: Implement processDeposit() method
    // TODO: Implement processWithdrawal() method
    // TODO: Implement issueCashierCheck() method
    // TODO: Implement balanceDrawer() method
    // TODO: Override hasPermission() method
    // TODO: Implement all getter and setter methods
}