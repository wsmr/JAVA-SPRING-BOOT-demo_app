// ========================================================================================
// CONTROLLER CLASSES
// ========================================================================================

// File: src/main/java/com/bankingsystem/controller/api/v1/AccountController.java
package com.bankingsystem.controller.api.v1;

import com.bankingsystem.service.interfaces.IAccountService;
import com.bankingsystem.dto.request.account.CreateAccountRequest;
import com.bankingsystem.dto.response.account.AccountResponse;
import com.bankingsystem.dto.response.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {
        // TODO: Implement account creation endpoint
        // TODO: Add security checks
        // TODO: Add input validation
        // TODO: Handle exceptions
        return null;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(
            @PathVariable String accountNumber) {
        // TODO: Implement get account endpoint
        // TODO: Add authorization checks
        // TODO: Handle not found cases
        return null;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getCustomerAccounts(
            @PathVariable String customerId) {
        // TODO: Implement get customer accounts endpoint
        // TODO: Add pagination support
        // TODO: Add filtering options
        return null;
    }
}