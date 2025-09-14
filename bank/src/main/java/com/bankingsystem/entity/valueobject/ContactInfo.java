// File: src/main/java/com/bankingsystem/entity/valueobject/ContactInfo.java
package com.bankingsystem.entity.valueobject;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class ContactInfo {

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\+?1?\\d{9,15}", message = "Invalid phone number")
    private String phoneNumber;

    @Pattern(regexp = "\\+?1?\\d{9,15}", message = "Invalid phone number")
    private String alternatePhone;

    @Enumerated(EnumType.STRING)
    private ContactMethod preferredContact = ContactMethod.EMAIL;

    // TODO: Implement all getter and setter methods
    // TODO: Implement validate() method
    // TODO: Implement getFormattedPhoneNumber() method
    // TODO: Implement equals() and hashCode() methods
}
