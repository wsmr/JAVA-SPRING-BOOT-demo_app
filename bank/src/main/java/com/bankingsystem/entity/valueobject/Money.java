// File: src/main/java/com/bankingsystem/entity/valueobject/Money.java
package com.bankingsystem.entity.valueobject;

import com.bankingsystem.enums.Currency;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public class Money {

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Money() {
        this.amount = BigDecimal.ZERO;
        this.currency = Currency.USD;
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    // TODO: Implement add() method
    // TODO: Implement subtract() method
    // TODO: Implement multiply() method
    // TODO: Implement divide() method
    // TODO: Implement isPositive() method
    // TODO: Implement isNegative() method
    // TODO: Implement convertTo() method for currency conversion
    // TODO: Implement equals() and hashCode() methods
}
