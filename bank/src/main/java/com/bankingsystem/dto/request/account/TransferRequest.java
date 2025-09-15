package com.bankingsystem.dto.request.account;

import com.bankingsystem.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Data Transfer Object for money transfer requests.
 * Contains all information needed to transfer money between accounts.
 */
@Schema(description = "Request object for transferring money between accounts")
public class TransferRequest {

    @Schema(description = "Source account number", example = "ACC-1234567890")
    @NotBlank(message = "Source account number is required")
    @Pattern(regexp = "^ACC-\\d{10}$", message = "Invalid source account number format")
    private String fromAccountNumber;

    @Schema(description = "Destination account number", example = "ACC-0987654321")
    @NotBlank(message = "Destination account number is required")
    @Pattern(regexp = "^ACC-\\d{10}$", message = "Invalid destination account number format")
    private String toAccountNumber;

    @Schema(description = "Transfer amount", example = "1000.00")
    @NotNull(message = "Transfer amount is required")
    @DecimalMin(value = "0.01", message = "Transfer amount must be greater than zero")
    @DecimalMax(value = "999999.99", message = "Transfer amount exceeds maximum limit")
    @Digits(integer = 6, fraction = 2, message = "Invalid amount format")
    private BigDecimal amount;

    @Schema(description = "Transfer currency", example = "USD")
    @NotNull(message = "Currency is required")
    private Currency currency;

    @Schema(description = "Transfer description or memo", example = "Payment for services")
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;

    @Schema(description = "Reference number for tracking", example = "REF-123456")
    @Size(max = 50, message = "Reference number cannot exceed 50 characters")
    private String referenceNumber;

    @Schema(description = "Whether transfer should be processed immediately", example = "true")
    private boolean immediate = true;

    @Schema(description = "Scheduled transfer date (if not immediate)", example = "2024-01-15")
    private String scheduledDate;

    // Default constructor
    public TransferRequest() {}

    // Constructor with required fields
    public TransferRequest(String fromAccountNumber, String toAccountNumber, BigDecimal amount, Currency currency) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.currency = currency;
    }

    // TODO: Implement all getter and setter methods
    // TODO: Add cross-field validation (e.g., scheduled date required if not immediate)
    // TODO: Implement custom validation for same-account transfers
    // TODO: Add validation for business hours and processing times

    // Getters and Setters
    public String getFromAccountNumber() { return fromAccountNumber; }
    public void setFromAccountNumber(String fromAccountNumber) { this.fromAccountNumber = fromAccountNumber; }

    public String getToAccountNumber() { return toAccountNumber; }
    public void setToAccountNumber(String toAccountNumber) { this.toAccountNumber = toAccountNumber; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public boolean isImmediate() { return immediate; }
    public void setImmediate(boolean immediate) { this.immediate = immediate; }

    public String getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(String scheduledDate) { this.scheduledDate = scheduledDate; }

    @Override
    public String toString() {
        return "TransferRequest{" +
                "fromAccountNumber='" + fromAccountNumber + '\'' +
                ", toAccountNumber='" + toAccountNumber + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", description='" + description + '\'' +
                ", immediate=" + immediate +
                '}';
    }
}