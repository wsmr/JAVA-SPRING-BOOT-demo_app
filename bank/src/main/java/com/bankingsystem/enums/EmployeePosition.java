// File: src/main/java/com/bankingsystem/enums/EmployeePosition.java
package com.bankingsystem.enums;

public enum EmployeePosition {
    TELLER("Bank Teller"),
    CUSTOMER_SERVICE("Customer Service Representative"),
    LOAN_OFFICER("Loan Officer"),
    BRANCH_MANAGER("Branch Manager"),
    ASSISTANT_MANAGER("Assistant Manager"),
    SECURITY_OFFICER("Security Officer"),
    IT_SPECIALIST("IT Specialist"),
    COMPLIANCE_OFFICER("Compliance Officer");

    private final String displayName;

    EmployeePosition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}