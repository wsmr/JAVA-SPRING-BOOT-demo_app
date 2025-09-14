// File: src/main/java/com/bankingsystem/entity/person/BankEmployee.java
package com.bankingsystem.entity.person;

import com.bankingsystem.enums.EmployeePosition;
import com.bankingsystem.entity.security.Permission;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "bank_employees")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("EMPLOYEE")
public abstract class BankEmployee extends Person {

    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeId;

    @Column(name = "department")
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private EmployeePosition position;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_permissions",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private BankEmployee supervisor;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<BankEmployee> subordinates;

    // TODO: Implement login() method
    // TODO: Implement processCustomerRequest() method
    // TODO: Implement generateReport() method
    // TODO: Override validatePerson() method
    // TODO: Implement abstract hasPermission() method
    // TODO: Implement all getter and setter methods
}