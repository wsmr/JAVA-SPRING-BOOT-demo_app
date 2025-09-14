// File: src/main/java/com/bankingsystem/entity/person/Customer.java
package com.bankingsystem.entity.person;

import com.bankingsystem.enums.CustomerType;
import com.bankingsystem.enums.RiskLevel;
import com.bankingsystem.entity.account.Account;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
@DiscriminatorValue("CUSTOMER")
public class Customer extends Person {

    @Column(name = "customer_id", unique = true, nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "customer_since")
    private LocalDate customerSince;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private RiskLevel riskLevel;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    // TODO: Implement openAccount() method
    // TODO: Implement closeAccount() method
    // TODO: Implement transferMoney() method
    // TODO: Implement getAccountsByType() method
    // TODO: Implement calculateTotalBalance() method
    // TODO: Implement upgradeCustomerType() method
    // TODO: Override validatePerson() method
    // TODO: Implement all getter and setter methods
}