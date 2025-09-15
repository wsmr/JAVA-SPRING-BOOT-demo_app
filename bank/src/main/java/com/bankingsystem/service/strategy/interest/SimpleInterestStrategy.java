// File: src/main/java/com/bankingsystem/service/strategy/interest/SimpleInterestStrategy.java
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
        // TODO: Implement simple interest calculation
        // Formula: Principal * Rate * Time / 365
        // TODO: Get account balance
        // TODO: Get interest rate based on account type
        // TODO: Calculate daily interest
        // TODO: Return total interest for the period

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

        return null;
    }

    @Override
    public BigDecimal getInterestRate(Account account) {

        // TODO: Return interest rate based on account type and customer type
        // TODO: Consider promotional rates
        // TODO: Consider balance tiers
        if (account instanceof CheckingAccount) {
            return CHECKING_RATE;
        }
        return BigDecimal.ZERO;

        return null;
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

//@Component
//public class SimpleInterestStrategy implements InterestCalculationStrategy {
//
//}
