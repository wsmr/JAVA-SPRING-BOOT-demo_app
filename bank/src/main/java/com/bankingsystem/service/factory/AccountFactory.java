// ========================================================================================
// FACTORY CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/service/factory/AccountFactory.java
package com.bankingsystem.service.factory;

import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.Customer;
import com.bankingsystem.enums.AccountType;
import org.springframework.stereotype.Component;

@Component
public abstract class AccountFactory {

    public static Account createAccount(AccountType type, Customer customer, Double initialDeposit) {
        // TODO: Implement factory method pattern
        // TODO: Validate account creation parameters
        // TODO: Delegate to specific factory based on account type
        // TODO: Set common account properties
        // TODO: Return created account
        return null;
    }

    protected abstract boolean validateAccountCreation(Customer customer, AccountType accountType);
}