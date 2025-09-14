// File: src/main/java/com/bankingsystem/exception/AccountNotFoundException.java
package com.bankingsystem.exception;

public class AccountNotFoundException extends BankingException {

    public AccountNotFoundException(String accountNumber) {
        super("ACCOUNT_NOT_FOUND",
                "Account not found: " + accountNumber,
                "The requested account could not be found");
    }
}