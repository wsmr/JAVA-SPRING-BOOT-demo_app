// ========================================================================================
// EXCEPTION CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/exception/BankingException.java
package com.bankingsystem.exception;

/**
 * Base exception class for all banking system exceptions.
 * Provides common error handling structure with error codes and user messages.
 */
public class BankingException extends RuntimeException {

    private final String errorCode;
    private final String userMessage;
    private final String technicalDetails;

    public BankingException(String message) {
        super(message);
        this.errorCode = "BANKING_ERROR";
        this.userMessage = message;
        this.technicalDetails = null;
    }

    // TODO: Implement all getter methods
    // TODO: Add additional constructors
    // TODO: Implement serialization support

    public BankingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = message;
        this.technicalDetails = null;
    }

    public BankingException(String errorCode, String message, String userMessage) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.technicalDetails = message;
    }

    public BankingException(String errorCode, String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.technicalDetails = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }

    @Override
    public String toString() {
        return "BankingException{" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}