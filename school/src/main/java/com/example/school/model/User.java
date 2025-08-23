package com.example.school.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "users") // This creates a "users" collection in MongoDB
public class User {

    @Id
    private String id; // MongoDB will auto-generate this as ObjectId

    @Field("first_name")
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Field("last_name")
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Indexed(unique = true) // Creates unique index on email field
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 150, message = "Age must be less than 150")
    private int age;

    @Field("phone_number")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please provide a valid phone number")
    private String phoneNumber;

    @Field("hobbies")
    private List<String> hobbies;

    @Field("address")
    private Address address; // Embedded document

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("is_active")
    private boolean isActive = true;

    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
    }

    public User(String firstName, String lastName, String email, int age) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    // Full constructor
    public User(String firstName, String lastName, String email, int age,
                String phoneNumber, List<String> hobbies, Address address) {
        this(firstName, lastName, email, age);
        this.phoneNumber = phoneNumber;
        this.hobbies = hobbies;
        this.address = address;
    }

    // Getters and Setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateTimestamp();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateTimestamp();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        updateTimestamp();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        updateTimestamp();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateTimestamp();
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
        updateTimestamp();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        updateTimestamp();
    }

    // Helper method to update timestamp
    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    // Utility methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean hasHobby(String hobby) {
        return hobbies != null && hobbies.contains(hobby);
    }

    public void addHobby(String hobby) {
        if (hobbies == null) {
            hobbies = new java.util.ArrayList<>();
        }
        if (!hobbies.contains(hobby)) {
            hobbies.add(hobby);
            updateTimestamp();
        }
    }

    public void removeHobby(String hobby) {
        if (hobbies != null) {
            hobbies.remove(hobby);
            updateTimestamp();
        }
    }

    // equals and hashCode based on email (business key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hobbies=" + hobbies +
                ", address=" + address +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                '}';
    }
}