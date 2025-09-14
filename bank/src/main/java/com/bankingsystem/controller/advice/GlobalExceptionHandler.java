// ========================================================================================
// GLOBAL EXCEPTION HANDLER
// ========================================================================================

// File: src/main/java/com/bankingsystem/controller/advice/GlobalExceptionHandler.java
package com.bankingsystem.controller.advice;

import com.bankingsystem.exception.BankingException;
import com.bankingsystem.exception.AccountNotFoundException;
import com.bankingsystem.exception.InsufficientFundsException;
import com.bankingsystem.dto.response.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        // TODO: Create error response with appropriate HTTP status
        // TODO: Log the exception
        // TODO: Return user-friendly error message
        return null;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        // TODO: Create error response for insufficient funds
        // TODO: Include available balance information if appropriate
        // TODO: Log the exception
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        // TODO: Extract field validation errors
        // TODO: Create user-friendly error messages
        // TODO: Return validation error map
        return null;
    }

    @ExceptionHandler(BankingException.class)
    public ResponseEntity<ErrorResponse> handleBankingException(BankingException ex) {
        // TODO: Handle general banking exceptions
        // TODO: Map error codes to HTTP status codes
        // TODO: Log the exception with appropriate level
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // TODO: Handle unexpected exceptions
        // TODO: Log the exception with error level
        // TODO: Return generic error message (don't expose internal details)
        return null;
    }
}