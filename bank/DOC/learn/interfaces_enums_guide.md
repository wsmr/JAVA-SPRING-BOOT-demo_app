# Complete Guide: Interfaces vs Enums in Banking System

## 🎯 **What are Interfaces and Why Do We Use Them?**

**Interfaces** define contracts that classes must follow. They specify **what** a class should do, but not **how** it should do it. This is fundamental to achieving loose coupling and high cohesion in software design.

### **Key Benefits of Interfaces:**
1. **Abstraction**: Hide implementation details
2. **Polymorphism**: Same interface, different implementations
3. **Loose Coupling**: Components depend on contracts, not concrete classes
4. **Testability**: Easy to mock and test
5. **Flexibility**: Switch implementations without changing client code
6. **Multiple Inheritance**: Java classes can implement multiple interfaces

## 🏗️ **Interface Usage in Banking System - Detailed Examples**

### **1. Service Layer Interfaces**

#### **File: `src/main/java/com/bankingsystem/service/interfaces/ITransactionService.java`**
```java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.dto.request.account.DepositRequest;
import com.bankingsystem.dto.request.account.WithdrawRequest;
import com.bankingsystem.dto.request.account.TransferRequest;
import com.bankingsystem.dto.response.transaction.TransactionResultResponse;
import java.util.List;

/**
 * Interface defining contract for transaction processing operations.
 * This abstraction allows different implementations (sync, async, batch processing)
 * while maintaining the same contract for client code.
 */
public interface ITransactionService {
    
    /**
     * Process a deposit transaction
     * @param request Contains account number, amount, and deposit details
     * @return Transaction result with success/failure status
     */
    TransactionResultResponse processDeposit(DepositRequest request);
    
    /**
     * Process a withdrawal transaction
     * @param request Contains account number, amount, and withdrawal details
     * @return Transaction result with updated balance
     */
    TransactionResultResponse processWithdrawal(WithdrawRequest request);
    
    /**
     * Transfer money between accounts
     * @param request Contains from/to accounts, amount, and transfer details
     * @return Transaction result for the transfer operation
     */
    TransactionResultResponse processTransfer(TransferRequest request);
    
    /**
     * Reverse a completed transaction
     * @param transactionId The transaction to reverse
     * @param reason Business reason for reversal
     * @return Result of the reversal operation
     */
    TransactionResultResponse reverseTransaction(String transactionId, String reason);
    
    /**
     * Get transaction history for an account
     * @param accountNumber Account to query
     * @param limit Maximum number of transactions to return
     * @return List of recent transactions
     */
    List<TransactionResultResponse> getTransactionHistory(String accountNumber, int limit);
}
```

#### **File: `src/main/java/com/bankingsystem/service/impl/TransactionServiceImpl.java`**
```java
package com.bankingsystem.service.impl;

import com.bankingsystem.service.interfaces.ITransactionService;
import com.bankingsystem.repository.transaction.TransactionRepository;
import com.bankingsystem.repository.account.AccountRepository;
import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.transaction.Transaction;
import com.bankingsystem.exception.InsufficientFundsException;
import com.bankingsystem.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standard implementation of transaction processing.
 * This is the default implementation used in production.
 */
@Service("standardTransactionService")
@Transactional
public class TransactionServiceImpl implements ITransactionService {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private INotificationService notificationService;
    
    @Override
    public TransactionResultResponse processDeposit(DepositRequest request) {
        logger.info("Processing deposit: accountNumber={}, amount={}", 
                   request.getAccountNumber(), request.getAmount());
        
        try {
            // Find account
            Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                    .orElseThrow(() -> new AccountNotFoundException(request.getAccountNumber()));
            
            // Validate deposit
            if (request.getAmount() <= 0) {
                throw new InvalidTransactionException("Deposit amount must be positive");
            }
            
            // Process deposit
            account.setBalance(account.getBalance() + request.getAmount());
            accountRepository.save(account);
            
            // Create transaction record
            Transaction transaction = createDepositTransaction(account, request);
            transactionRepository.save(transaction);
            
            // Send notification
            notificationService.sendTransactionAlert(transaction);
            
            logger.info("Deposit processed successfully: transactionId={}", transaction.getTransactionId());
            
            return TransactionResultResponse.success(
                transaction.getTransactionId(),
                "Deposit processed successfully",
                account.getBalance()
            );
            
        } catch (Exception e) {
            logger.error("Deposit processing failed: {}", e.getMessage(), e);
            return TransactionResultResponse.failure("DEPOSIT_FAILED", e.getMessage());
        }
    }
    
    // Other method implementations...
    
    private Transaction createDepositTransaction(Account account, DepositRequest request) {
        // Implementation for creating deposit transaction entity
        return null; // TODO: Implement
    }
}
```

#### **File: `src/main/java/com/bankingsystem/service/impl/AsyncTransactionServiceImpl.java`**
```java
package com.bankingsystem.service.impl;

import com.bankingsystem.service.interfaces.ITransactionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

/**
 * Asynchronous implementation of transaction processing.
 * Used for high-volume batch processing or when immediate response isn't required.
 */
@Service("asyncTransactionService")
public class AsyncTransactionServiceImpl implements ITransactionService {
    
    @Async
    @Override
    public TransactionResultResponse processDeposit(DepositRequest request) {
        // Asynchronous processing logic
        // Queue transaction for batch processing
        // Return immediate acknowledgment
        return TransactionResultResponse.queued(
            generateTransactionId(),
            "Transaction queued for processing"
        );
    }
    
    // Other async implementations...
}
```

### **2. Strategy Pattern Interfaces**

#### **File: `src/main/java/com/bankingsystem/service/interfaces/IInterestCalculator.java`**
```java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.entity.account.Account;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Strategy interface for different interest calculation methods.
 * Allows switching between simple interest, compound interest, tiered rates, etc.
 */
public interface IInterestCalculator {
    
    /**
     * Calculate interest for an account over a specific period
     * @param account The account to calculate interest for
     * @param fromDate Start date for calculation
     * @param toDate End date for calculation
     * @return Calculated interest amount
     */
    BigDecimal calculateInterest(Account account, LocalDate fromDate, LocalDate toDate);
    
    /**
     * Get the current interest rate for an account
     * @param account Account to get rate for
     * @return Annual interest rate as decimal (e.g., 0.025 for 2.5%)
     */
    BigDecimal getInterestRate(Account account);
    
    /**
     * Get compounding frequency (times per year)
     * @return Number of times interest is compounded annually
     */
    Integer getCompoundingFrequency();
    
    /**
     * Determine if this calculator applies to the given account
     * @param account Account to check
     * @return true if this calculator should be used for the account
     */
    boolean appliesTo(Account account);
}
```

#### **File: `src/main/java/com/bankingsystem/service/strategy/interest/SimpleInterestStrategy.java`**
```java
package com.bankingsystem.service.strategy.interest;

import com.bankingsystem.service.interfaces.IInterestCalculator;
import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.account.CheckingAccount;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

/**
 * Simple interest calculation strategy.
 * Formula: Interest = Principal × Rate × Time
 * Used for checking accounts and short-term deposits.
 */
@Component
public class SimpleInterestStrategy implements IInterestCalculator {
    
    private static final BigDecimal DAYS_IN_YEAR = new BigDecimal("365");
    private static final BigDecimal CHECKING_RATE = new BigDecimal("0.001"); // 0.1%
    
    @Override
    public BigDecimal calculateInterest(Account account, LocalDate fromDate, LocalDate toDate) {
        BigDecimal principal = new BigDecimal(account.getBalance().toString());
        BigDecimal rate = getInterestRate(account);
        
        // Calculate days between dates
        Period period = Period.between(fromDate, toDate);
        BigDecimal days = new BigDecimal(period.getDays());
        
        // Simple Interest Formula: P × R × T
        BigDecimal interest = principal
            .multiply(rate)
            .multiply(days)
            .divide(DAYS_IN_YEAR, 2, RoundingMode.HALF_UP);
            
        return interest;
    }
    
    @Override
    public BigDecimal getInterestRate(Account account) {
        if (account instanceof CheckingAccount) {
            return CHECKING_RATE;
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public Integer getCompoundingFrequency() {
        return 1; // No compounding for simple interest
    }
    
    @Override
    public boolean appliesTo(Account account) {
        return account instanceof CheckingAccount;
    }
}
```

#### **File: `src/main/java/com/bankingsystem/service/strategy/interest/CompoundInterestStrategy.java`**
```java
package com.bankingsystem.service.strategy.interest;

import com.bankingsystem.service.interfaces.IInterestCalculator;
import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.account.SavingsAccount;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Compound interest calculation strategy.
 * Formula: A = P(1 + r/n)^(nt) - P
 * Used for savings accounts and long-term investments.
 */
@Component
public class CompoundInterestStrategy implements IInterestCalculator {
    
    private static final BigDecimal SAVINGS_RATE = new BigDecimal("0.025"); // 2.5%
    private static final Integer MONTHLY_COMPOUNDING = 12;
    
    @Override
    public BigDecimal calculateInterest(Account account, LocalDate fromDate, LocalDate toDate) {
        BigDecimal principal = new BigDecimal(account.getBalance().toString());
        BigDecimal rate = getInterestRate(account);
        Integer compoundingFreq = getCompoundingFrequency();
        
        // Calculate time in years
        long days = java.time.temporal.ChronoUnit.DAYS.between(fromDate, toDate);
        BigDecimal timeInYears = new BigDecimal(days).divide(new BigDecimal("365"), 6, RoundingMode.HALF_UP);
        
        // Compound Interest Formula: A = P(1 + r/n)^(nt)
        BigDecimal ratePerPeriod = rate.divide(new BigDecimal(compoundingFreq), 6, RoundingMode.HALF_UP);
        BigDecimal onePlusRate = BigDecimal.ONE.add(ratePerPeriod);
        BigDecimal exponent = timeInYears.multiply(new BigDecimal(compoundingFreq));
        
        // A = P × (1 + r/n)^(nt)
        BigDecimal finalAmount = principal.multiply(
            onePlusRate.pow(exponent.intValue())
        );
        
        // Interest = A - P
        return finalAmount.subtract(principal).setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public BigDecimal getInterestRate(Account account) {
        if (account instanceof SavingsAccount) {
            SavingsAccount savings = (SavingsAccount) account;
            return new BigDecimal(savings.getInterestRate().toString());
        }
        return SAVINGS_RATE;
    }
    
    @Override
    public Integer getCompoundingFrequency() {
        return MONTHLY_COMPOUNDING;
    }
    
    @Override
    public boolean appliesTo(Account account) {
        return account instanceof SavingsAccount;
    }
}
```

### **3. Repository Interfaces**

#### **File: `src/main/java/com/bankingsystem/repository/account/AccountRepository.java`**
```java
package com.bankingsystem.repository.account;

import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.Customer;
import com.bankingsystem.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Account entity operations.
 * Extends JpaRepository to get basic CRUD operations.
 * Custom methods are automatically implemented by Spring Data JPA.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    
    /**
     * Find account by account number
     * Spring Data JPA automatically generates implementation based on method name
     */
    Optional<Account> findByAccountNumber(String accountNumber);
    
    /**
     * Find all accounts owned by a customer
     */
    List<Account> findByOwner(Customer owner);
    
    /**
     * Find all accounts with specific status
     */
    List<Account> findByStatus(AccountStatus status);
    
    /**
     * Find accounts with balance below specified amount
     * Custom query using @Query annotation
     */
    @Query("SELECT a FROM Account a WHERE a.balance < :amount")
    List<Account> findAccountsWithLowBalance(@Param("amount") Double amount);
    
    /**
     * Find active accounts for a customer
     * Method name convention: findBy + Property + And/Or + Property + Condition
     */
    List<Account> findByOwnerAndStatus(Customer owner, AccountStatus status);
    
    /**
     * Count accounts by customer type
     * Native SQL query example
     */
    @Query(value = "SELECT COUNT(*) FROM accounts a JOIN customers c ON a.customer_id = c.id WHERE c.customer_type = :customerType", nativeQuery = true)
    Long countAccountsByCustomerType(@Param("customerType") String customerType);
    
    /**
     * Find accounts created in date range
     */
    @Query("SELECT a FROM Account a WHERE a.openDate BETWEEN :startDate AND :endDate")
    List<Account> findAccountsCreatedBetween(@Param("startDate") java.time.LocalDate startDate, 
                                           @Param("endDate") java.time.LocalDate endDate);
}
```

### **4. Factory Interface Pattern**

#### **File: `src/main/java/com/bankingsystem/service/interfaces/IAccountFactory.java`**
```java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.Customer;
import com.bankingsystem.enums.AccountType;
import java.math.BigDecimal;

/**
 * Factory interface for creating different types of accounts.
 * Implements Factory Method pattern with polymorphic account creation.
 */
public interface IAccountFactory {
    
    /**
     * Create an account of specified type
     * @param accountType Type of account to create
     * @param customer Account owner
     * @param initialDeposit Initial deposit amount
     * @return Created account instance
     */
    Account createAccount(AccountType accountType, Customer customer, BigDecimal initialDeposit);
    
    /**
     * Validate if account can be created for customer
     * @param accountType Type of account
     * @param customer Customer requesting account
     * @return true if account creation is allowed
     */
    boolean canCreateAccount(AccountType accountType, Customer customer);
    
    /**
     * Get minimum deposit required for account type
     * @param accountType Type of account
     * @return Minimum deposit amount
     */
    BigDecimal getMinimumDeposit(AccountType accountType);
    
    /**
     * Check if factory supports this account type
     * @param accountType Type to check
     * @return true if this factory can create this account type
     */
    boolean supports(AccountType accountType);
}
```

## 📊 **What are Enums and When to Use Them?**

**Enums** (Enumerations) represent a fixed set of constants. They're perfect for representing state, types, categories, or any finite set of values that won't change frequently.

### **Key Benefits of Enums:**
1. **Type Safety**: Prevent invalid values at compile time
2. **Readability**: Self-documenting code
3. **Maintainability**: Centralized constant definitions
4. **IDE Support**: Auto-completion and refactoring support
5. **Immutability**: Enum values cannot be changed
6. **Built-in Methods**: Automatic toString(), equals(), hashCode()

## 🎯 **Enum Usage in Banking System - Detailed Examples**

### **File: `src/main/java/com/bankingsystem/enums/AccountStatus.java`**
```java
package com.bankingsystem.enums;

/**
 * Enumeration of possible account statuses.
 * Represents the lifecycle states of a bank account.
 */
public enum AccountStatus {
    ACTIVE("Active", "Account is operational and can perform transactions"),
    INACTIVE("Inactive", "Account exists but cannot perform transactions"),
    FROZEN("Frozen", "Account is temporarily suspended due to security concerns"),
    CLOSED("Closed", "Account has been permanently closed"),
    SUSPENDED("Suspended", "Account is temporarily suspended due to policy violations"),
    PENDING_VERIFICATION("Pending Verification", "Account awaiting identity verification"),
    OVERDRAWN("Overdrawn", "Account balance is negative");
    
    private final String displayName;
    private final String description;
    
    // Constructor for enum constants
    AccountStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    // Getter methods
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Business logic methods
    public boolean canPerformTransactions() {
        return this == ACTIVE;
    }
    
    public boolean isClosedOrSuspended() {
        return this == CLOSED || this == SUSPENDED || this == FROZEN;
    }
    
    public boolean requiresVerification() {
        return this == PENDING_VERIFICATION;
    }
    
    // Static utility methods
    public static AccountStatus fromString(String status) {
        for (AccountStatus accountStatus : AccountStatus.values()) {
            if (accountStatus.name().equalsIgnoreCase(status) || 
                accountStatus.getDisplayName().equalsIgnoreCase(status)) {
                return accountStatus;
            }
        }
        throw new IllegalArgumentException("Invalid account status: " + status);
    }
    
    // Get all active statuses
    public static AccountStatus[] getActiveStatuses() {
        return new AccountStatus[]{ACTIVE, OVERDRAWN};
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
```

### **File: `src/main/java/com/bankingsystem/enums/TransactionType.java`**
```java
package com.bankingsystem.enums;

/**
 * Enumeration of transaction types in the banking system.
 * Used for categorization, reporting, and business logic decisions.
 */
public enum TransactionType {
    // Basic transaction types
    DEPOSIT("Deposit", "Money added to account", true, false),
    WITHDRAWAL("Withdrawal", "Money removed from account", false, true),
    TRANSFER_IN("Transfer In", "Money received from another account", true, false),
    TRANSFER_OUT("Transfer Out", "Money sent to another account", false, true),
    
    // Fee and interest transactions
    MONTHLY_FEE("Monthly Fee", "Regular account maintenance fee", false, true),
    OVERDRAFT_FEE("Overdraft Fee", "Fee charged for negative balance", false, true),
    INTEREST_EARNED("Interest Earned", "Interest credited to account", true, false),
    INTEREST_CHARGED("Interest Charged", "Interest debited from account", false, true),
    
    // Specialized transactions
    CHECK_DEPOSIT("Check Deposit", "Deposit via check", true, false),
    ATM_WITHDRAWAL("ATM Withdrawal", "Cash withdrawal from ATM", false, true),
    WIRE_TRANSFER_IN("Wire Transfer In", "Incoming wire transfer", true, false),
    WIRE_TRANSFER_OUT("Wire Transfer Out", "Outgoing wire transfer", false, true),
    DIRECT_DEPOSIT("Direct Deposit", "Payroll or government deposit", true, false),
    ACH_DEBIT("ACH Debit", "Automated clearing house debit", false, true),
    ACH_CREDIT("ACH Credit", "Automated clearing house credit", true, false),
    
    // Adjustment transactions
    ADJUSTMENT_CREDIT("Adjustment Credit", "Manual credit adjustment", true, false),
    ADJUSTMENT_DEBIT("Adjustment Debit", "Manual debit adjustment", false, true),
    REVERSAL("Reversal", "Transaction reversal", false, false);
    
    private final String displayName;
    private final String description;
    private final boolean increasesBalance;
    private final boolean decreasesBalance;
    
    TransactionType(String displayName, String description, boolean increasesBalance, boolean decreasesBalance) {
        this.displayName = displayName;
        this.description = description;
        this.increasesBalance = increasesBalance;
        this.decreasesBalance = decreasesBalance;
    }
    
    // Getter methods
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public boolean increasesBalance() { return increasesBalance; }
    public boolean decreasesBalance() { return decreasesBalance; }
    
    // Business logic methods
    public boolean isDebitTransaction() {
        return decreasesBalance;
    }
    
    public boolean isCreditTransaction() {
        return increasesBalance;
    }
    
    public boolean isFeeTransaction() {
        return this == MONTHLY_FEE || this == OVERDRAFT_FEE;
    }
    
    public boolean isInterestTransaction() {
        return this == INTEREST_EARNED || this == INTEREST_CHARGED;
    }
    
    public boolean isTransferTransaction() {
        return this == TRANSFER_IN || this == TRANSFER_OUT || 
               this == WIRE_TRANSFER_IN || this == WIRE_TRANSFER_OUT;
    }
    
    public boolean requiresApproval() {
        return this == WIRE_TRANSFER_OUT || this == ADJUSTMENT_CREDIT || 
               this == ADJUSTMENT_DEBIT;
    }
    
    // Static utility methods
    public static TransactionType[] getDepositTypes() {
        return new TransactionType[]{DEPOSIT, CHECK_DEPOSIT, DIRECT_DEPOSIT, 
                                   WIRE_TRANSFER_IN, ACH_CREDIT};
    }
    
    public static TransactionType[] getWithdrawalTypes() {
        return new TransactionType[]{WITHDRAWAL, ATM_WITHDRAWAL, 
                                   WIRE_TRANSFER_OUT, ACH_DEBIT};
    }
    
    public static TransactionType fromString(String type) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.name().equalsIgnoreCase(type) || 
                transactionType.getDisplayName().equalsIgnoreCase(type)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Invalid transaction type: " + type);
    }
}
```

### **File: `src/main/java/com/bankingsystem/enums/CustomerType.java`**
```java
package com.bankingsystem.enums;

import java.math.BigDecimal;

/**
 * Customer tier levels with associated benefits and limits.
 * Used for business logic decisions and personalized services.
 */
public enum CustomerType {
    BASIC("Basic Customer", 0, new BigDecimal("1000"), new BigDecimal("500"), 0),
    PREMIUM("Premium Customer", 5000, new BigDecimal("5000"), new BigDecimal("2000"), 1),
    VIP("VIP Customer", 25000, new BigDecimal("25000"), new BigDecimal("10000"), 3),
    BUSINESS("Business Customer", 0, new BigDecimal("100000"), new BigDecimal("50000"), 5);
    
    private final String displayName;
    private final int minimumBalance;
    private final BigDecimal dailyWithdrawalLimit;
    private final BigDecimal dailyTransferLimit;
    private final int freeTransactionsPerMonth;
    
    CustomerType(String displayName, int minimumBalance, 
                BigDecimal dailyWithdrawalLimit, BigDecimal dailyTransferLimit, 
                int freeTransactionsPerMonth) {
        this.displayName = displayName;
        this.minimumBalance = minimumBalance;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.dailyTransferLimit = dailyTransferLimit;
        this.freeTransactionsPerMonth = freeTransactionsPerMonth;
    }
    
    // Getter methods
    public String getDisplayName() { return displayName; }
    public int getMinimumBalance() { return minimumBalance; }
    public BigDecimal getDailyWithdrawalLimit() { return dailyWithdrawalLimit; }
    public BigDecimal getDailyTransferLimit() { return dailyTransferLimit; }
    public int getFreeTransactionsPerMonth() { return freeTransactionsPerMonth; }
    
    // Business logic methods
    public boolean hasWithdrawalFees() {
        return this == BASIC;
    }
    
    public boolean hasMonthlyFees() {
        return this == BASIC && minimumBalance < 1000;
    }
    
    public BigDecimal getOverdraftLimit() {
        switch (this) {
            case BASIC: return BigDecimal.ZERO;
            case PREMIUM: return new BigDecimal("500");
            case VIP: return new BigDecimal("2000");
            case BUSINESS: return new BigDecimal("5000");
            default: return BigDecimal.ZERO;
        }
    }
    
    public double getInterestRateBonus() {
        switch (this) {
            case BASIC: return 0.0;
            case PREMIUM: return 0.0025; // +0.25%
            case VIP: return 0.005;      // +0.5%
            case BUSINESS: return 0.0;
            default: return 0.0;
        }
    }
    
    // Upgrade/downgrade logic
    public CustomerType getNextTier() {
        switch (this) {
            case BASIC: return PREMIUM;
            case PREMIUM: return VIP;
            case VIP: return VIP; // Already at top
            case BUSINESS: return BUSINESS; // Different track
            default: return this;
        }
    }
    
    public boolean canUpgradeTo(CustomerType targetType) {
        if (this == BUSINESS || targetType == BUSINESS) {
            return false; // Business is separate track
        }
        return targetType.ordinal() > this.ordinal();
    }
    
    // Static utility methods
    public static CustomerType determineTypeByBalance(BigDecimal totalBalance) {
        if (totalBalance.compareTo(new BigDecimal("25000")) >= 0) {
            return VIP;
        } else if (totalBalance.compareTo(new BigDecimal("5000")) >= 0) {
            return PREMIUM;
        } else {
            return BASIC;
        }
    }
}
```

## 🔍 **Key Differences: Interfaces vs Enums**

| Aspect | Interfaces | Enums |
|--------|------------|--------|
| **Purpose** | Define contracts/behavior | Define fixed constants/states |
| **Implementation** | Classes implement interfaces | Pre-defined constant values |
| **Flexibility** | Multiple implementations possible | Fixed set of values |
| **Inheritance** | Classes can implement multiple | Cannot extend other classes |
| **Memory** | No memory allocation | Each enum constant is an instance |
| **Usage** | Polymorphism, abstraction | State representation, categorization |
| **Methods** | Abstract methods (default implementations allowed) | Concrete methods on enum instances |
| **Instantiation** | Cannot be instantiated directly | Enum constants are singleton instances |

## 📁 **File Organization Strategy**

### **Directory Structure for Interfaces:**
```
src/main/java/com/bankingsystem/
├── service/
│   ├── interfaces/           # Service contracts
│   │   ├── IAccountService.java
│   │   ├── ITransactionService.java
│   │   ├── ICustomerService.java
│   │   ├── INotificationService.java
│   │   ├── IInterestCalculator.java
│   │   ├── IFeeCalculator.java
│   │   └── IAuditService.java
│   │
│   ├── impl/                 # Service implementations
│   │   ├── AccountServiceImpl.java
│   │   ├── TransactionServiceImpl.java
│   │   └── AsyncTransactionServiceImpl.java
│   │
│   └── strategy/             # Strategy pattern interfaces & implementations
│       ├── interest/
│       │   ├── InterestCalculationStrategy.java
│       │   ├── SimpleInterestStrategy.java
│       │   └── CompoundInterestStrategy.java
│       └── fee/
│           ├── FeeCalculationStrategy.java
│           ├── StandardFeeStrategy.java
│           └── PremiumFeeStrategy.java
```

### **Directory Structure for Enums:**
```
src/main/java/com/bankingsystem/
├── enums/                    # All enum definitions
│   ├── AccountStatus.java    # Account lifecycle states
│   ├── AccountType.java      # Types of accounts
│   ├── TransactionType.java  # Transaction categories
│   ├── TransactionStatus.java # Transaction states
│   ├── CustomerType.java     # Customer tiers
│   ├