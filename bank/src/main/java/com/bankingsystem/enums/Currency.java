// File: src/main/java/com/bankingsystem/enums/Currency.java
package com.bankingsystem.enums;

public enum Currency {
    USD("US Dollar", "$"),
    EUR("Euro", "€"),
    GBP("British Pound", "£"),
    CAD("Canadian Dollar", "C$"),
    AUD("Australian Dollar", "A$"),
    JPY("Japanese Yen", "¥");

    private final String displayName;
    private final String symbol;

    Currency(String displayName, String symbol) {
        this.displayName = displayName;
        this.symbol = symbol;
    }

    public String getDisplayName() { return displayName; }
    public String getSymbol() { return symbol; }
}
