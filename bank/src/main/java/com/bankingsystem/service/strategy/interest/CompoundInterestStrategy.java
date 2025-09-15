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