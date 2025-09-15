// File: src/main/java/com/bankingsystem/dto/response/account/AccountResponse.java
package com.bankingsystem.dto.response.account;

import com.bankingsystem.enums.AccountStatus;
import com.bankingsystem.enums.AccountType;
import com.bankingsystem.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for account information responses.
 * Contains account details returned to API clients.
 */
@Schema(description = "Response object containing account information")
public class AccountResponse {

//    private String accountId;
//private String status;

    @Schema(description = "Unique account identifier", example = "ACC-1234567890")
    private String accountNumber;

    @Schema(description = "Account display name or nickname", example = "Primary Savings")
    private String accountName;

    @Schema(description = "Type of account", example = "SAVINGS")
    private AccountType accountType;

    @Schema(description = "Current account status", example = "ACTIVE")
    private AccountStatus status;

    @Schema(description = "Account currency", example = "USD")
    private Currency currency;

    @Schema(description = "Current account balance", example = "2500.75")
    private BigDecimal balance;

    @Schema(description = "Available balance for transactions", example = "2400.75")
    private BigDecimal availableBalance;

    @Schema(description = "Date when account was opened", example = "2023-01-15")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    @Schema(description = "Date of last transaction", example = "2024-01-10T14:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastTransactionDate;

    @Schema(description = "Customer ID who owns the account", example = "CUST-123456")
    private String customerId;

    @Schema(description = "Customer full name", example = "John Doe")
    private String customerName;

    @Schema(description = "Interest rate for the account", example = "2.5")
    private BigDecimal interestRate;

    @Schema(description = "Minimum balance requirement", example = "100.00")
    private BigDecimal minimumBalance;

    @Schema(description = "Daily transaction limit", example = "5000.00")
    private BigDecimal dailyLimit;

    @Schema(description = "Monthly maintenance fee", example = "10.00")
    private BigDecimal monthlyFee;

    // Default constructor
    public AccountResponse() {}

    // Builder pattern for easy construction
    public static class Builder {
        private AccountResponse response = new AccountResponse();

        public Builder accountNumber(String accountNumber) {
            response.accountNumber = accountNumber;
            return this;
        }

        public Builder accountName(String accountName) {
            response.accountName = accountName;
            return this;
        }

        public Builder accountType(AccountType accountType) {
            response.accountType = accountType;
            return this;
        }

        public Builder status(AccountStatus status) {
            response.status = status;
            return this;
        }

        public Builder currency(Currency currency) {
            response.currency = currency;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            response.balance = balance;
            return this;
        }

        public Builder availableBalance(BigDecimal availableBalance) {
            response.availableBalance = availableBalance;
            return this;
        }

        public Builder openDate(LocalDate openDate) {
            response.openDate = openDate;
            return this;
        }

        public Builder lastTransactionDate(LocalDateTime lastTransactionDate) {
            response.lastTransactionDate = lastTransactionDate;
            return this;
        }

        public Builder customerId(String customerId) {
            response.customerId = customerId;
            return this;
        }

        public Builder customerName(String customerName) {
            response.customerName = customerName;
            return this;
        }

        public AccountResponse build() {
            return response;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // TODO: Implement all getter and setter methods
    // TODO: Add computed fields (e.g., account age, transaction count)
    // TODO: Implement security filtering for sensitive fields


    // TODO: Implement all getter and setter methods
    // TODO: Add builder pattern
    // TODO: Implement toString() method

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public BigDecimal getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }

    public LocalDate getOpenDate() { return openDate; }
    public void setOpenDate(LocalDate openDate) { this.openDate = openDate; }

    public LocalDateTime getLastTransactionDate() { return lastTransactionDate; }
    public void setLastTransactionDate(LocalDateTime lastTransactionDate) { this.lastTransactionDate = lastTransactionDate; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", status=" + status +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}