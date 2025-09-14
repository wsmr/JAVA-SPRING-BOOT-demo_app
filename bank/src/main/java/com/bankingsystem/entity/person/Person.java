// ========================================================================================
// PERSON HIERARCHY
// ========================================================================================

// File: src/main/java/com/bankingsystem/entity/person/Person.java
package com.bankingsystem.entity.person;

import com.bankingsystem.entity.base.AuditableEntity;
import com.bankingsystem.entity.valueobject.Address;
import com.bankingsystem.entity.valueobject.ContactInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type")
public abstract class Person extends AuditableEntity {

    @NotBlank
    @Column(name = "person_id", unique = true, nullable = false)
    private String personId;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Past
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "state", column = @Column(name = "address_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "address_zip")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country"))
    })
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number")),
            @AttributeOverride(name = "alternatePhone", column = @Column(name = "alternate_phone"))
    })
    private ContactInfo contactInfo;

    // TODO: Implement getFullName() method
    // TODO: Implement getAge() calculation method
    // TODO: Implement updateAddress() method with validation
    // TODO: Implement abstract validatePerson() method
    // TODO: Implement all getter and setter methods
}
