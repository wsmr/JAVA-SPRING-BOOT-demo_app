// ========================================================================================
// CONTROLLER CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/controller/api/v1/AccountController.java
package com.bankingsystem.controller.api.v1;

import com.bankingsystem.service.interfaces.IAccountService;
import com.bankingsystem.dto.request.account.CreateAccountRequest;
import com.bankingsystem.dto.request.account.DepositRequest;
import com.bankingsystem.dto.request.account.WithdrawRequest;
import com.bankingsystem.dto.response.account.AccountResponse;
import com.bankingsystem.dto.response.account.BalanceResponse;
import com.bankingsystem.dto.response.common.ApiResponse;
import com.bankingsystem.dto.response.common.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * REST Controller for Account Management operations.
 * Handles account creation, balance inquiries, and basic account operations.
 */
@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Management", description = "APIs for managing bank accounts")
@Validated
@CrossOrigin(origins = "${app.cors.allowed-origins}", maxAge = 3600)
public class AccountController {

    @Autowired
    private IAccountService accountService;

    /**
     * Create a new bank account
     */
    @PostMapping
    @Operation(summary = "Create new account", description = "Creates a new bank account for a customer")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Account created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Account already exists")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        // TODO: Implement account creation logic
        // TODO: Validate customer eligibility
        // TODO: Apply account creation business rules
        // TODO: Generate account number
        // TODO: Set up initial account configuration
        // TODO: Send welcome notification

        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully", response));


        // TODO: Implement account creation endpoint
        // TODO: Add security checks
        // TODO: Add input validation
        // TODO: Handle exceptions

        return null;
    }

    /**
     * Get account details by account number
     */
    @GetMapping("/{accountNumber}")
    @Operation(summary = "Get account details", description = "Retrieves account information by account number")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(
            @Parameter(description = "Account number")
            @PathVariable @NotBlank String accountNumber) {

        // TODO: Implement account retrieval logic
        // TODO: Validate user authorization to access account
        // TODO: Apply privacy rules for account information

        AccountResponse response = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(ApiResponse.success("Account retrieved successfully", response));


        // TODO: Implement get account endpoint
        // TODO: Add authorization checks
        // TODO: Handle not found cases
        return null;
    }

    /**
     * Get account balance
     */
    @GetMapping("/{accountNumber}/balance")
    @Operation(summary = "Get account balance", description = "Retrieves current account balance")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(
            @PathVariable @NotBlank String accountNumber) {

        // TODO: Implement balance inquiry logic
        // TODO: Check real-time balance including pending transactions
        // TODO: Apply balance visibility rules

        BalanceResponse response = accountService.getAccountBalance(accountNumber);
        return ResponseEntity.ok(ApiResponse.success("Balance retrieved successfully", response));
    }

    /**
     * Get accounts by customer
     */
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get customer accounts", description = "Retrieves all accounts for a customer")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<PagedResponse<AccountResponse>>> getCustomerAccounts(
            @PathVariable @NotBlank String customerId,
            Pageable pageable) {

        // TODO: Implement customer account listing
        // TODO: Apply pagination and sorting
        // TODO: Filter based on user permissions

        PagedResponse<AccountResponse> response = accountService.getAccountsByCustomer(customerId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Customer accounts retrieved successfully", response));

        // TODO: Implement get customer accounts endpoint
        // TODO: Add pagination support
        // TODO: Add filtering options
        return null;
    }

    /**
     * Deposit money to account
     */
    @PostMapping("/{accountNumber}/deposit")
    @Operation(summary = "Deposit money", description = "Deposits money into an account")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<AccountResponse>> deposit(
            @PathVariable @NotBlank String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        // TODO: Implement deposit logic
        // TODO: Validate deposit amount and currency
        // TODO: Apply deposit limits and business rules
        // TODO: Process deposit transaction
        // TODO: Update account balance
        // TODO: Send deposit confirmation

        AccountResponse response = accountService.processDeposit(accountNumber, request);
        return ResponseEntity.ok(ApiResponse.success("Deposit processed successfully", response));
    }

    /**
     * Withdraw money from account
     */
    @PostMapping("/{accountNumber}/withdraw")
    @Operation(summary = "Withdraw money", description = "Withdraws money from an account")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<AccountResponse>> withdraw(
            @PathVariable @NotBlank String accountNumber,
            @Valid @RequestBody WithdrawRequest request) {

        // TODO: Implement withdrawal logic
        // TODO: Validate sufficient balance
        // TODO: Check withdrawal limits
        // TODO: Apply overdraft rules if applicable
        // TODO: Process withdrawal transaction
        // TODO: Update account balance
        // TODO: Send withdrawal notification

        AccountResponse response = accountService.processWithdrawal(accountNumber, request);
        return ResponseEntity.ok(ApiResponse.success("Withdrawal processed successfully", response));
    }

    /**
     * Freeze account
     */
    @PutMapping("/{accountNumber}/freeze")
    @Operation(summary = "Freeze account", description = "Freezes an account to prevent transactions")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AccountResponse>> freezeAccount(
            @PathVariable @NotBlank String accountNumber,
            @RequestParam(required = false) String reason) {

        // TODO: Implement account freezing logic
        // TODO: Validate authorization to freeze account
        // TODO: Apply freeze business rules
        // TODO: Update account status
        // TODO: Log freeze action for audit
        // TODO: Notify account holder

        AccountResponse response = accountService.freezeAccount(accountNumber, reason);
        return ResponseEntity.ok(ApiResponse.success("Account frozen successfully", response));
    }

    /**
     * Unfreeze account
     */
    @PutMapping("/{accountNumber}/unfreeze")
    @Operation(summary = "Unfreeze account", description = "Unfreezes an account to allow transactions")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AccountResponse>> unfreezeAccount(
            @PathVariable @NotBlank String accountNumber,
            @RequestParam(required = false) String reason) {

        // TODO: Implement account unfreezing logic
        // TODO: Validate authorization to unfreeze account
        // TODO: Apply unfreeze business rules
        // TODO: Update account status
        // TODO: Log unfreeze action for audit
        // TODO: Notify account holder

        AccountResponse response = accountService.unfreezeAccount(accountNumber, reason);
        return ResponseEntity.ok(ApiResponse.success("Account unfrozen successfully", response));
    }
}