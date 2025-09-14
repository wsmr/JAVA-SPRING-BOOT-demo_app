// ========================================================================================
// DTO CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/dto/request/account/CreateAccountRequest.java
package com.bankingsystem.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateAccountRequest {

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotNull(message = "Initial deposit is required")
    @Positive(message = "Initial deposit must be positive")
    private Double initialDeposit;

    private String currency = "USD";

    // TODO: Implement all getter and setter methods
    // TODO: Add validation annotations
    // TODO: Implement toString() method
}