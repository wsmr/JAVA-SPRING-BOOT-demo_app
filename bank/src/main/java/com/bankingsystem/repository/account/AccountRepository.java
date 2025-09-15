// File: src/main/java/com/bankingsystem/repository/account/AccountRepository.java
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

    // TODO: Add methods for account balance queries
    // TODO: Add methods for account type filtering
    // TODO: Add methods for dormant account identification
}