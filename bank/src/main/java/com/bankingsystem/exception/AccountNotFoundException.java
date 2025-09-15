// File: src/main/java/com/bankingsystem/exception/AccountNotFoundException.java
package com.bankingsystem.exception;

/**
 * Exception thrown when a requested account cannot be found.
 */
public class AccountNotFoundException extends BankingException {

    public AccountNotFoundException(String accountNumber) {
        super("ACCOUNT_NOT_FOUND",
                "Account not found with number: " + accountNumber,
                "The requested account could not be found. Please verify the account number.");
    }

    public AccountNotFoundException(String accountNumber, Throwable cause) {
        super("ACCOUNT_NOT_FOUND",
                "Account not found with number: " + accountNumber,
                "The requested account could not be found. Please verify the account number.",
                cause);
    }
}