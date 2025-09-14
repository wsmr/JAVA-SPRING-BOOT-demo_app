// File: src/main/java/com/bankingsystem/repository/account/AccountRepository.java
package com.bankingsystem.repository.account;

import com.bankingsystem.entity.account.Account;
import com.bankingsystem.entity.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByOwner(Customer owner);

    List<Account> findByStatus(String status);

    @Query("SELECT a FROM Account a WHERE a.balance < :amount")
    List<Account> findAccountsWithLowBalance(Double amount);

    // TODO: Add methods for account balance queries
    // TODO: Add methods for account type filtering
    // TODO: Add methods for dormant account identification
}