package com.bankingsystem.service.interfaces;

import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.Customer;
import com.bankingsystem.enums.AccountType;
import java.math.BigDecimal;

/**
 * Factory interface for creating different types of accounts.
 * Implements Factory Method pattern with polymorphic account creation.
 */
public interface IAccountFactory {

    /**
     * Create an account of specified type
     * @param accountType Type of account to create
     * @param customer Account owner
     * @param initialDeposit Initial deposit amount
     * @return Created account instance
     */
    Account createAccount(AccountType accountType, Customer customer, BigDecimal initialDeposit);

    /**
     * Validate if account can be created for customer
     * @param accountType Type of account
     * @param customer Customer requesting account
     * @return true if account creation is allowed
     */
    boolean canCreateAccount(AccountType accountType, Customer customer);

    /**
     * Get minimum deposit required for account type
     * @param accountType Type of account
     * @return Minimum deposit amount
     */
    BigDecimal getMinimumDeposit(AccountType accountType);

    /**
     * Check if factory supports this account type
     * @param accountType Type to check
     * @return true if this factory can create this account type
     */
    boolean supports(AccountType accountType);
}