// ========================================================================================
// REPOSITORY INTERFACES
// ========================================================================================

// File: src/main/java/com/bankingsystem/repository/person/CustomerRepository.java
package com.bankingsystem.repository.person;

import com.bankingsystem.entity.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByCustomerId(String customerId);

    Optional<Customer> findByContactInfoEmail(String email);

    List<Customer> findByCustomerType(String customerType);

    @Query("SELECT c FROM Customer c WHERE c.creditScore >= :minScore")
    List<Customer> findByMinimumCreditScore(@Param("minScore") Integer minScore);

    // TODO: Add custom query methods for customer search
    // TODO: Add methods for customer analytics
    // TODO: Add methods for risk assessment queries
}