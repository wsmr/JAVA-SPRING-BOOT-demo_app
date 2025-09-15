package com.bankingsystem.controller.api.v1;

import com.bankingsystem.service.interfaces.ITransactionService;
import com.bankingsystem.dto.request.transaction.TransferRequest;
import com.bankingsystem.dto.request.transaction.TransactionSearchRequest;
import com.bankingsystem.dto.response.transaction.TransactionResponse;
import com.bankingsystem.dto.response.transaction.TransactionResultResponse;
import com.bankingsystem.dto.response.common.ApiResponse;
import com.bankingsystem.dto.response.common.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * REST Controller for Transaction operations.
 * Handles money transfers, transaction history, and transaction management.
 */
@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Management", description = "APIs for managing financial transactions")
@CrossOrigin(origins = "${app.cors.allowed-origins}", maxAge = 3600)
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    /**
     * Transfer money between accounts
     */
    @PostMapping("/transfer")
    @Operation(summary = "Transfer money", description = "Transfers money between two accounts")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<TransactionResultResponse>> transferMoney(
            @Valid @RequestBody TransferRequest request) {

        // TODO: Implement money transfer logic
        // TODO: Validate source and destination accounts
        // TODO: Check transfer limits and restrictions
        // TODO: Verify sufficient balance
        // TODO: Apply transfer fees
        // TODO: Process transfer transaction atomically
        // TODO: Send transfer notifications

        TransactionResultResponse response = transactionService.processTransfer(request);
        return ResponseEntity.ok(ApiResponse.success("Transfer completed successfully", response));
    }

    /**
     * Get transaction history
     */
    @GetMapping("/account/{accountNumber}")
    @Operation(summary = "Get transaction history", description = "Retrieves transaction history for an account")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<PagedResponse<TransactionResponse>>> getTransactionHistory(
            @PathVariable @NotBlank String accountNumber,
            @Valid TransactionSearchRequest searchRequest,
            Pageable pageable) {

        // TODO: Implement transaction history retrieval
        // TODO: Apply date range filtering
        // TODO: Apply transaction type filtering
        // TODO: Apply amount range filtering
        // TODO: Ensure user authorization for account access
        // TODO: Apply pagination and sorting

        PagedResponse<TransactionResponse> response = transactionService.getTransactionHistory(
                accountNumber, searchRequest, pageable);
        return ResponseEntity.ok(ApiResponse.success("Transaction history retrieved successfully", response));
    }

    /**
     * Get transaction details
     */
    @GetMapping("/{transactionId}")
    @Operation(summary = "Get transaction details", description = "Retrieves details of a specific transaction")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransaction(
            @PathVariable @NotBlank String transactionId) {

        // TODO: Implement transaction detail retrieval
        // TODO: Validate user authorization to access transaction
        // TODO: Include related transaction details

        TransactionResponse response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(ApiResponse.success("Transaction details retrieved successfully", response));
    }

    /**
     * Reverse a transaction
     */
    @PostMapping("/{transactionId}/reverse")
    @Operation(summary = "Reverse transaction", description = "Reverses a completed transaction")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<TransactionResultResponse>> reverseTransaction(
            @PathVariable @NotBlank String transactionId,
            @RequestParam @NotBlank String reason) {

        // TODO: Implement transaction reversal logic
        // TODO: Validate transaction can be reversed
        // TODO: Check reversal time limits
        // TODO: Process reversal transaction
        // TODO: Update account balances
        // TODO: Log reversal for audit
        // TODO: Send reversal notifications

        TransactionResultResponse response = transactionService.reverseTransaction(transactionId, reason);
        return ResponseEntity.ok(ApiResponse.success("Transaction reversed successfully", response));
    }
}