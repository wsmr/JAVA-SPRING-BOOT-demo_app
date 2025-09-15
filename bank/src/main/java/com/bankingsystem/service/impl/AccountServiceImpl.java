// ========================================================================================
// SERVICE IMPLEMENTATIONS
// ========================================================================================

// File: src/main/java/com/bankingsystem/service/impl/AccountServiceImpl.java
package com.bankingsystem.service.impl;

import com.bankingsystem.service.interfaces.IAccountService;
import com.bankingsystem.service.interfaces.INotificationService;
import com.bankingsystem.service.interfaces.IAuditService;
import com.bankingsystem.service.factory.AccountFactory;
import com.bankingsystem.repository.account.AccountRepository;
import com.bankingsystem.repository.person.CustomerRepository;
import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.Customer;
import com.bankingsystem.dto.request.account.CreateAccountRequest;
import com.bankingsystem.dto.request.account.DepositRequest;
import com.bankingsystem.dto.response.account.AccountResponse;
import com.bankingsystem.dto.response.account.BalanceResponse;
import com.bankingsystem.dto.response.common.PagedResponse;
import com.bankingsystem.dto.mapper.AccountMapper;
import com.bankingsystem.exception.AccountNotFoundException;
import com.bankingsystem.exception.CustomerNotFoundException;
import com.bankingsystem.exception.InvalidTransactionException;
import com.bankingsystem.exception.InsufficientFundsException;
import com.bankingsystem.enums.AccountStatus;
import com.bankingsystem.enums.TransactionType;
import com.bankingsystem.util.AccountNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Account Service.
 * Handles all business logic related to account management.
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountNumberGenerator accountNumberGenerator;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IAuditService auditService;

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        logger.info("Creating account for customer: {}, type: {}",
                request.getCustomerId(), request.getAccountType());

        try {
            // TODO: Validate customer exists and is eligible
            Customer customer = customerRepository.findByCustomerId(request.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

            // TODO: Validate account creation business rules
            validateAccountCreationRules(customer, request);

            // TODO: Create account using factory
            Account account = accountFactory.createAccount(
                    request.getAccountType(), customer, request.getInitialDeposit());

            // TODO: Generate unique account number
            String accountNumber = accountNumberGenerator.generateAccountNumber(
                    request.getAccountType().name());
            account.setAccountNumber(accountNumber);

            // TODO: Set account properties
            account.setCurrency(request.getCurrency());
            account.setBalance(request.getInitialDeposit());
            account.setStatus(AccountStatus.ACTIVE);
            account.setOpenDate(LocalDate.now());

            // TODO: Save account to database
            Account savedAccount = accountRepository.save(account);

            // TODO: Create initial deposit transaction record
            createInitialDepositTransaction(savedAccount, request.getInitialDeposit());

            // TODO: Send welcome notification
            notificationService.sendAccountCreationNotification(customer, savedAccount);

            // TODO: Log account creation for audit
            auditService.logAccountCreation(savedAccount, customer);

            logger.info("Account created successfully: {}", savedAccount.getAccountNumber());

            return accountMapper.toResponse(savedAccount);

        } catch (Exception e) {
            logger.error("Failed to create account for customer: {}", request.getCustomerId(), e);
            throw e;
        }


        // TODO: Validate customer exists
        // TODO: Create account using factory
        // TODO: Generate account number
        // TODO: Set initial deposit
        // TODO: Save account to database
        // TODO: Send welcome notification
        // TODO: Return account response
        return null;
    }

    @Override
    @Cacheable(value = "accounts", key = "#accountNumber")
    public AccountResponse getAccountByNumber(String accountNumber) {
        logger.debug("Retrieving account: {}", accountNumber);

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // TODO: Apply security checks for account access
        validateAccountAccess(account);

        return accountMapper.toResponse(account);


        // TODO: Find account by number
        // TODO: Check user authorization
        // TODO: Map entity to response DTO
        // TODO: Handle not found case
        return null;
    }

    @Override
    public PagedResponse<AccountResponse> getAccountsByCustomer(String customerId, Pageable pageable) {
        logger.debug("Retrieving accounts for customer: {}", customerId);

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Page<Account> accountPage = accountRepository.findByOwner(customer, pageable);

        List<AccountResponse> accountResponses = accountPage.getContent().stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                accountResponses,
                accountPage.getNumber(),
                accountPage.getSize(),
                accountPage.getTotalElements(),
                accountPage.getTotalPages(),
                accountPage.isFirst(),
                accountPage.isLast()
        );

        // TODO: Find customer by ID
        // TODO: Get all accounts for customer
        // TODO: Check user authorization
        // TODO: Map entities to response DTOs
        return null;
    }

    @Override
    @CacheEvict(value = "accounts", key = "#accountNumber")
    @Transactional
    public AccountResponse processDeposit(String accountNumber, DepositRequest request) {
        logger.info("Processing deposit: account={}, amount={}", accountNumber, request.getAmount());

        try {
            // TODO: Find and validate account
            Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new AccountNotFoundException(accountNumber));

            // TODO: Validate deposit request
            validateDepositRequest(account, request);

            // TODO: Update account balance
            BigDecimal newBalance = account.getBalance().add(request.getAmount());
            account.setBalance(newBalance);
            account.setLastTransactionDate(LocalDateTime.now());

            // TODO: Save updated account
            Account updatedAccount = accountRepository.save(account);

            // TODO: Create transaction record
            createDepositTransaction(updatedAccount, request);

            // TODO: Send deposit notification
            notificationService.sendTransactionNotification(
                    updatedAccount.getOwner(), TransactionType.DEPOSIT, request.getAmount());

            // TODO: Log deposit for audit
            auditService.logTransaction(updatedAccount, TransactionType.DEPOSIT, request.getAmount());

            logger.info("Deposit processed successfully: account={}, newBalance={}",
                    accountNumber, newBalance);

            return accountMapper.toResponse(updatedAccount);

        } catch (Exception e) {
            logger.error("Failed to process deposit: account={}", accountNumber, e);
            throw e;
        }
    }

    @Override
    @CacheEvict(value = "accounts", key = "#accountNumber")
    @Transactional
    public AccountResponse processWithdrawal(String accountNumber, WithdrawRequest request) {
        logger.info("Processing withdrawal: account={}, amount={}", accountNumber, request.getAmount());

        try {
            // TODO: Find and validate account
            Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new AccountNotFoundException(accountNumber));

            // TODO: Validate withdrawal request
            validateWithdrawalRequest(account, request);

            // TODO: Check sufficient balance
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                throw new InsufficientFundsException(accountNumber, account.getBalance(), request.getAmount());
            }

            // TODO: Update account balance
            BigDecimal newBalance = account.getBalance().subtract(request.getAmount());
            account.setBalance(newBalance);
            account.setLastTransactionDate(LocalDateTime.now());

            // TODO: Save updated account
            Account updatedAccount = accountRepository.save(account);

            // TODO: Create transaction record
            createWithdrawalTransaction(updatedAccount, request);

            // TODO: Send withdrawal notification
            notificationService.sendTransactionNotification(
                    updatedAccount.getOwner(), TransactionType.WITHDRAWAL, request.getAmount());

            // TODO: Log withdrawal for audit
            auditService.logTransaction(updatedAccount, TransactionType.WITHDRAWAL, request.getAmount());

            logger.info("Withdrawal processed successfully: account={}, newBalance={}",
                    accountNumber, newBalance);

            return accountMapper.toResponse(updatedAccount);

        } catch (Exception e) {
            logger.error("Failed to process withdrawal: account={}", accountNumber, e);
            throw e;
        }
    }

    @Override
    @CacheEvict(value = "accounts", key = "#accountNumber")
    @Transactional
    public AccountResponse freezeAccount(String accountNumber, String reason) {
        logger.info("Freezing account: {}, reason: {}", accountNumber, reason);

        try {
            Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new AccountNotFoundException(accountNumber));

            // TODO: Validate account can be frozen
            if (account.getStatus() == AccountStatus.FROZEN) {
                throw new InvalidTransactionException("Account is already frozen");
            }

            if (account.getStatus() == AccountStatus.CLOSED) {
                throw new InvalidTransactionException("Cannot freeze a closed account");
            }

            // TODO: Update account status
            account.setStatus(AccountStatus.FROZEN);
            Account updatedAccount = accountRepository.save(account);

            // TODO: Send freeze notification
            notificationService.sendAccountStatusChangeNotification(
                    updatedAccount.getOwner(), updatedAccount, AccountStatus.FROZEN, reason);

            // TODO: Log freeze action for audit
            auditService.logAccountStatusChange(updatedAccount, AccountStatus.FROZEN, reason);

            logger.info("Account frozen successfully: {}", accountNumber);

            return accountMapper.toResponse(updatedAccount);

        } catch (Exception e) {
            logger.error("Failed to freeze account: {}", accountNumber, e);
            throw e;
        }
    }



    @Override
    public AccountResponse updateAccountStatus(String accountNumber, String status) {
        // TODO: Find account by number
        // TODO: Validate status change
        // TODO: Update account status
        // TODO: Log status change
        // TODO: Send notification
        return null;
    }

    @Override
    public Double calculateInterestForAccount(String accountNumber) {
        // TODO: Find account by number
        // TODO: Get interest calculator for account type
        // TODO: Calculate interest based on balance and time
        // TODO: Return calculated interest
        return null;
    }

    @Override
    public boolean closeAccount(String accountNumber, String reason) {
        // TODO: Find account by number
        // TODO: Validate account can be closed
        // TODO: Transfer remaining balance if needed
        // TODO: Update account status to closed
        // TODO: Log closure reason
        // TODO: Send closure notification
        return false;
    }

    // Private helper methods
    private void validateAccountCreationRules(Customer customer, CreateAccountRequest request) {
        // TODO: Implement account creation validation rules
        // - Check customer eligibility
        // - Validate minimum deposit requirements
        // - Check account limits per customer
        // - Verify customer risk level
    }

    private void validateAccountAccess(Account account) {
        // TODO: Implement security checks for account access
        // - Verify user has permission to access this account
        // - Check account visibility rules
        // - Apply data privacy regulations
    }

    private void validateDepositRequest(Account account, DepositRequest request) {
        // TODO: Implement deposit validation rules
        // - Check account is active and can receive deposits
        // - Validate deposit amount limits
        // - Verify currency compatibility
        // - Check for suspicious activity patterns
    }

    private void validateWithdrawalRequest(Account account, WithdrawRequest request) {
        // TODO: Implement withdrawal validation rules
        // - Check account is active and allows withdrawals
        // - Validate withdrawal limits (daily, monthly)
        // - Verify currency compatibility
        // - Check for suspicious activity patterns
    }

    private void createInitialDepositTransaction(Account account, BigDecimal amount) {
        // TODO: Create initial deposit transaction record
        // - Generate transaction ID
        // - Set transaction type to DEPOSIT
        // - Record transaction details
        // - Link to account
    }

    private void createDepositTransaction(Account account, DepositRequest request) {
        // TODO: Create deposit transaction record
        // - Generate transaction ID
        // - Set transaction details from request
        // - Link to account
        // - Set processing status
    }

    private void createWithdrawalTransaction(Account account, WithdrawRequest request) {
        // TODO: Create withdrawal transaction record
        // - Generate transaction ID
        // - Set transaction details from request
        // - Link to account
        // - Set processing status
    }
}