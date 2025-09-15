// ========================================================================================
// DTO CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/dto/request/account/CreateAccountRequest.java
package com.bankingsystem.dto.request.account;

import com.bankingsystem.enums.AccountType;
import com.bankingsystem.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Data Transfer Object for account creation requests.
 * Contains all necessary information to create a new bank account.
 */
@Schema(description = "Request object for creating a new bank account")
public class CreateAccountRequest {

    @Schema(description = "Customer ID who owns the account", example = "CUST-123456")
    @NotBlank(message = "Customer ID is required")
    @Size(min = 5, max = 20, message = "Customer ID must be between 5 and 20 characters")
    private String customerId;

//    @NotBlank(message = "Account type is required")
//    private String accountType;

    @Schema(description = "Type of account to create", example = "SAVINGS")
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @Schema(description = "Initial deposit amount", example = "500.00")
    @NotNull(message = "Initial deposit is required")
//    @Positive(message = "Initial deposit must be positive")
    @DecimalMin(value = "0.01", message = "Initial deposit must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid amount format")
    private BigDecimal initialDeposit;

    @Schema(description = "Account currency", example = "USD")
    @NotNull(message = "Currency is required")
    private Currency currency;

    @Schema(description = "Account nickname for identification", example = "Emergency Savings")
    @Size(max = 50, message = "Nickname cannot exceed 50 characters")
    private String nickname;

    @Schema(description = "Special account features or preferences")
    private String specialInstructions;

    // Default constructor
    public CreateAccountRequest() {}

    // Constructor with required fields
    public CreateAccountRequest(String customerId, AccountType accountType, BigDecimal initialDeposit, Currency currency) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
        this.currency = currency;
    }

    // TODO: Implement all getter and setter methods
    // TODO: Add validation groups for different validation scenarios
    // TODO: Implement toString() method excluding sensitive data
    // TODO: Add custom validation annotations if needed

    // TODO: Implement all getter and setter methods
    // TODO: Add validation annotations
    // TODO: Implement toString() method

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public BigDecimal getInitialDeposit() { return initialDeposit; }
    public void setInitialDeposit(BigDecimal initialDeposit) { this.initialDeposit = initialDeposit; }

    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "customerId='" + customerId + '\'' +
                ", accountType=" + accountType +
                ", initialDeposit=" + initialDeposit +
                ", currency=" + currency +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}