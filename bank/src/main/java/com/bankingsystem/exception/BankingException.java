// ========================================================================================
// EXCEPTION CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/exception/BankingException.java
package com.bankingsystem.exception;

public class BankingException extends RuntimeException {

    private String errorCode;
    private String userMessage;

    public BankingException(String message) {
        super(message);
    }

    public BankingException(String errorCode, String message, String userMessage) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }

    // TODO: Implement all getter methods
    // TODO: Add additional constructors
    // TODO: Implement serialization support
}