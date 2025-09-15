// ========================================================================================
// SERVICE INTERFACES
// ========================================================================================

// File: src/main/java/com/bankingsystem/service/interfaces/IAccountService.java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.dto.request.account.CreateAccountRequest;
import com.bankingsystem.dto.request.account.DepositRequest;
import com.bankingsystem.dto.request.account.WithdrawRequest;
import com.bankingsystem.dto.response.account.AccountResponse;
import com.bankingsystem.dto.response.account.BalanceResponse;
import com.bankingsystem.dto.response.common.PagedResponse;
import com.bankingsystem.enums.AccountStatus;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for Account operations.
 * Defines business logic contracts for account management.
 */
public interface IAccountService {

    /**
     * Create a new account
     * @param request Account creation details
     * @return Created account information
     */
    AccountResponse createAccount(CreateAccountRequest request);

    /**
     * Get account by account number
     * @param accountNumber Account number to retrieve
     * @return Account information
     */
    AccountResponse getAccountByNumber(String accountNumber);

    /**
     * Get accounts by customer ID
     * @param customerId Customer identifier
     * @param pageable Pagination information
     * @return Paginated list of customer accounts
     */
    PagedResponse<AccountResponse> getAccountsByCustomer(String customerId, Pageable pageable);

    /**
     * Get account balance
     * @param accountNumber Account number
     * @return Current balance information
     */
    BalanceResponse getAccountBalance(String accountNumber);

    /**
     * Process deposit transaction
     * @param accountNumber Target account
     * @param request Deposit details
     * @return Updated account information
     */
    AccountResponse processDeposit(String accountNumber, DepositRequest request);

    /**
     * Process withdrawal transaction
     * @param accountNumber Source account
     * @param request Withdrawal details
     * @return Updated account information
     */
    AccountResponse processWithdrawal(String accountNumber, WithdrawRequest request);

    /**
     * Freeze account to prevent transactions
     * @param accountNumber Account to freeze
     * @param reason Reason for freezing
     * @return Updated account information
     */
    AccountResponse freezeAccount(String accountNumber, String reason);

    /**
     * Unfreeze account to allow transactions
     * @param accountNumber Account to unfreeze
     * @param reason Reason for unfreezing
     * @return Updated account information
     */
    AccountResponse unfreezeAccount(String accountNumber, String reason);

    /**
     * Close account permanently
     * @param accountNumber Account to close
     * @param reason Reason for closure
     * @return Closure confirmation
     */
    boolean closeAccount(String accountNumber, String reason);

    /**
     * Update account status
     * @param accountNumber Account to update
     * @param status New status
     * @return Updated account information
     */
    AccountResponse updateAccountStatus(String accountNumber, AccountStatus status);

    /**
     * Calculate interest for account
     * @param accountNumber Account to calculate interest for
     * @return Calculated interest amount
     */
    BigDecimal calculateInterestForAccount(String accountNumber);

    /**
     * Apply interest to account
     * @param accountNumber Account to apply interest to
     * @return Updated account information
     */
    AccountResponse applyInterestToAccount(String accountNumber);

    /**
     * Get accounts with low balance
     * @param threshold Minimum balance threshold
     * @return List of accounts below threshold
     */
    List<AccountResponse> getAccountsWithLowBalance(BigDecimal threshold);

    /**
     * Get dormant accounts
     * @param daysSinceLastTransaction Number of days of inactivity
     * @return List of dormant accounts
     */
    List<AccountResponse> getDormantAccounts(int daysSinceLastTransaction);

    /**
     * Generate account statement
     * @param accountNumber Account to generate statement for
     * @param startDate Statement start date
     * @param endDate Statement end date
     * @return Account statement data
     */
    AccountStatement generateAccountStatement(String accountNumber, String startDate, String endDate);
}