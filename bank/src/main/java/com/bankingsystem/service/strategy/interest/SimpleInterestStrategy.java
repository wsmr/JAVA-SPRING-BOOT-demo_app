// File: src/main/java/com/bankingsystem/service/strategy/interest/SimpleInterestStrategy.java
package com.bankingsystem.service.strategy.interest;

import com.bankingsystem.entity.account.Account;
import org.springframework.stereotype.Component;

@Component
public class SimpleInterestStrategy implements InterestCalculationStrategy {

    @Override
    public Double calculateInterest(Account account, Integer days) {
        // TODO: Implement simple interest calculation
        // Formula: Principal * Rate * Time / 365
        // TODO: Get account balance
        // TODO: Get interest rate based on account type
        // TODO: Calculate daily interest
        // TODO: Return total interest for the period
        return null;
    }

    @Override
    public Double getInterestRate(Account account) {
        // TODO: Return interest rate based on account type and customer type
        // TODO: Consider promotional rates
        // TODO: Consider balance tiers
        return null;
    }

    @Override
    public Integer getCompoundingFrequency() {
        return 1; // Simple interest - no compounding
    }
}
