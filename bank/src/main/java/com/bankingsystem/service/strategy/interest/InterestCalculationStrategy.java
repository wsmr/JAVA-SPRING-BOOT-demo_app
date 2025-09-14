// ========================================================================================
// STRATEGY PATTERN IMPLEMENTATIONS
// ========================================================================================

// File: src/main/java/com/bankingsystem/service/strategy/interest/InterestCalculationStrategy.java
package com.bankingsystem.service.strategy.interest;

import com.bankingsystem.entity.account.Account;

public interface InterestCalculationStrategy {

    // TODO: Define calculateInterest method
    Double calculateInterest(Account account, Integer days);

    // TODO: Define getInterestRate method
    Double getInterestRate(Account account);

    // TODO: Define compound frequency method
    Integer getCompoundingFrequency();
}