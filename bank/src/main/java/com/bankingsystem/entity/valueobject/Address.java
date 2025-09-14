// ========================================================================================
// VALUE OBJECT CLASSES
// ========================================================================================


// File: src/main/java/com/bankingsystem/entity/valueobject/Address.java
package com.bankingsystem.entity.valueobject;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class Address {

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "Invalid zip code format")
    private String zipCode;

    @NotBlank(message = "Country is required")
    private String country = "United States";

    // TODO: Implement all getter and setter methods
    // TODO: Implement toString() method for address formatting
    // TODO: Implement validate() method for address validation
    // TODO: Implement equals() and hashCode() methods
}
