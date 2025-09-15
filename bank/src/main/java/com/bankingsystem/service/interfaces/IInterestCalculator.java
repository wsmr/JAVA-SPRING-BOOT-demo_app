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