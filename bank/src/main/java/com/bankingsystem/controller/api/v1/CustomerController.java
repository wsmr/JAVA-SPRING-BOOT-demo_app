package com.bankingsystem.controller.api.v1;

import com.bankingsystem.service.interfaces.ICustomerService;
import com.bankingsystem.dto.request.customer.CreateCustomerRequest;
import com.bankingsystem.dto.request.customer.UpdateCustomerRequest;
import com.bankingsystem.dto.response.customer.CustomerResponse;
import com.bankingsystem.dto.response.customer.CustomerProfileResponse;
import com.bankingsystem.dto.response.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * REST Controller for Customer Management operations.
 * Handles customer registration, profile management, and customer services.
 */
@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Management", description = "APIs for managing customer information")
@CrossOrigin(origins = "${app.cors.allowed-origins}", maxAge = 3600)
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    /**
     * Register new customer
     */
    @PostMapping("/register")
    @Operation(summary = "Register customer", description = "Registers a new customer")
    public ResponseEntity<ApiResponse<CustomerResponse>> registerCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        // TODO: Implement customer registration logic
        // TODO: Validate unique email and phone
        // TODO: Perform identity verification
        // TODO: Generate customer ID
        // TODO: Set initial customer type
        // TODO: Create user account for authentication
        // TODO: Send welcome email

        CustomerResponse response = customerService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Customer registered successfully", response));
    }

    /**
     * Get customer profile
     */
    @GetMapping("/{customerId}")
    @Operation(summary = "Get customer profile", description = "Retrieves customer profile information")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<CustomerProfileResponse>> getCustomerProfile(
            @PathVariable @NotBlank String customerId) {

        // TODO: Implement customer profile retrieval
        // TODO: Validate user authorization to access profile
        // TODO: Apply data privacy rules
        // TODO: Include related account information

        CustomerProfileResponse response = customerService.getCustomerProfile(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer profile retrieved successfully", response));
    }

    /**
     * Update customer profile
     */
    @PutMapping("/{customerId}")
    @Operation(summary = "Update customer profile", description = "Updates customer profile information")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomerProfile(
            @PathVariable @NotBlank String customerId,
            @Valid @RequestBody UpdateCustomerRequest request) {

        // TODO: Implement customer profile update logic
        // TODO: Validate user authorization to update profile
        // TODO: Verify identity for sensitive changes
        // TODO: Update customer information
        // TODO: Log profile changes for audit
        // TODO: Send update confirmation

        CustomerResponse response = customerService.updateCustomerProfile(customerId, request);
        return ResponseEntity.ok(ApiResponse.success("Customer profile updated successfully", response));
    }
}