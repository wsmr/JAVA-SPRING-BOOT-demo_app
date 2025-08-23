package com.example.school.service;

import com.example.school.model.User;
import com.example.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create Operations

    /**
     * Create a new user
     * @param user User object to create
     * @return Created user with generated ID
     * @throws RuntimeException if user with email already exists
     */
    public User createUser(User user) {
        // Validate business rules
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        // Check if user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        // Check if phone number already exists (if provided)
        if (user.getPhoneNumber() != null && userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("User with phone number " + user.getPhoneNumber() + " already exists");
        }

        // Set audit fields
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);

        return userRepository.save(user);
    }

    /**
     * Create multiple users in batch
     * @param users List of users to create
     * @return List of created users
     */
    public List<User> createUsers(List<User> users) {
        // Validate all users before saving
        for (User user : users) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("User with email " + user.getEmail() + " already exists");
            }
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setActive(true);
        }

        return userRepository.saveAll(users);
    }

    // Read Operations

    /**
     * Get all users
     * @return List of all users
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get all users with pagination
     * @param pageable Pagination information
     * @return Page of users
     */
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Get user by ID
     * @param id User ID
     * @return Optional containing user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Get user by email
     * @param email User email
     * @return Optional containing user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get all active users
     * @return List of active users
     */
    @Transactional(readOnly = true)
    public List<User> getActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    /**
     * Get active users with pagination
     * @param pageable Pagination information
     * @return Page of active users
     */
    @Transactional(readOnly = true)
    public Page<User> getActiveUsers(Pageable pageable) {
        return userRepository.findByIsActiveTrue(pageable);
    }

    // Update Operations

    /**
     * Update user
     * @param id User ID to update
     * @param updatedUser Updated user data
     * @return Updated user
     * @throws RuntimeException if user not found
     */
    public User updateUser(String id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        User existingUser = existingUserOpt.get();

        // Check if email is being changed and if new email already exists
        if (updatedUser.getEmail() != null &&
                !updatedUser.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new RuntimeException("User with email " + updatedUser.getEmail() + " already exists");
        }

        // Update fields if provided (null check to allow partial updates)
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getAge() > 0) {
            existingUser.setAge(updatedUser.getAge());
        }
        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getHobbies() != null) {
            existingUser.setHobbies(updatedUser.getHobbies());
        }
        if (updatedUser.getAddress() != null) {
            existingUser.setAddress(updatedUser.getAddress());
        }

        // Update timestamp
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    /**
     * Activate user
     * @param id User ID to activate
     * @return Updated user
     */
    public User activateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    /**
     * Deactivate user (soft delete)
     * @param id User ID to deactivate
     * @return Updated user
     */
    public User deactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Delete Operations

    /**
     * Delete user by ID (hard delete)
     * @param id User ID to delete
     * @throws RuntimeException if user not found
     */
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Delete user by email
     * @param email User email to delete
     * @throws RuntimeException if user not found
     */
    public void deleteUserByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found with email: " + email);
        }
        userRepository.deleteByEmail(email);
    }

    /**
     * Delete all inactive users
     * @return Number of deleted users
     */
    public long deleteInactiveUsers() {
        long count = userRepository.countByIsActiveFalse();
        userRepository.deleteByIsActiveFalse();
        return count;
    }

    // Search Operations

    /**
     * Search users by first name (case insensitive)
     * @param firstName First name to search
     * @return List of matching users
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }

    /**
     * Search users by name (first or last name)
     * @param name Name to search
     * @return List of matching users
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByName(String name) {
        return userRepository.searchByName(name);
    }

    /**
     * Get users by age range
     * @param minAge Minimum age
     * @param maxAge Maximum age
     * @return List of users in age range
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByAgeRange(int minAge, int maxAge) {
        if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
            throw new IllegalArgumentException("Invalid age range");
        }
        return userRepository.findByAgeBetween(minAge, maxAge);
    }

    /**
     * Get users by age range with pagination
     * @param minAge Minimum age
     * @param maxAge Maximum age
     * @param pageable Pagination information
     * @return Page of users in age range
     */
    @Transactional(readOnly = true)
    public Page<User> getUsersByAgeRange(int minAge, int maxAge, Pageable pageable) {
        if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
            throw new IllegalArgumentException("Invalid age range");
        }
        return userRepository.findByAgeBetween(minAge, maxAge, pageable);
    }

    /**
     * Get users with specific hobby
     * @param hobby Hobby to search
     * @return List of users with the hobby
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByHobby(String hobby) {
        return userRepository.findByHobbiesContaining(hobby);
    }

    /**
     * Get users by city
     * @param city City name
     * @return List of users in the city
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByCity(String city) {
        return userRepository.findByCity(city);
    }

    /**
     * Get users by country
     * @param country Country name
     * @return List of users in the country
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByCountry(String country) {
        return userRepository.findByCountry(country);
    }

    /**
     * Get users by email domain
     * @param domain Email domain (e.g., "@gmail.com")
     * @return List of users with emails from the domain
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByEmailDomain(String domain) {
        return userRepository.findByEmailDomain(domain);
    }

    // Statistics and Analytics

    /**
     * Get total user count
     * @return Total number of users
     */
    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }

    /**
     * Get active user count
     * @return Number of active users
     */
    @Transactional(readOnly = true)
    public long getActiveUserCount() {
        return userRepository.countByIsActiveTrue();
    }

    /**
     * Get users count by age range
     * @param minAge Minimum age
     * @param maxAge Maximum age
     * @return Number of users in age range
     */
    @Transactional(readOnly = true)
    public long getUserCountByAgeRange(int minAge, int maxAge) {
        return userRepository.countByAgeBetween(minAge, maxAge);
    }

    /**
     * Get users count by city
     * @param city City name
     * @return Number of users in the city
     */
    @Transactional(readOnly = true)
    public long getUserCountByCity(String city) {
        return userRepository.countByCity(city);
    }

    /**
     * Check if user exists by email
     * @param email Email to check
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check if user exists by phone number
     * @param phoneNumber Phone number to check
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean userExistsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    /**
     * Get users created after specific date
     * @param date Date to search from
     * @return List of users created after the date
     */
    @Transactional(readOnly = true)
    public List<User> getUsersCreatedAfter(LocalDateTime date) {
        return userRepository.findUsersCreatedAfter(date);
    }

    /**
     * Get most popular hobbies
     * @return List of popular hobbies with counts
     */
    @Transactional(readOnly = true)
    public List<Object> getMostPopularHobbies() {
        return userRepository.getMostPopularHobbies();
    }

    /**
     * Get average age by city
     * @return List of cities with average ages
     */
    @Transactional(readOnly = true)
    public List<Object> getAverageAgeByCity() {
        return userRepository.getAverageAgeByCity();
    }

    /**
     * Get user count by age groups (decades)
     * @return List of age groups with counts
     */
    @Transactional(readOnly = true)
    public List<Object> getUserCountByAgeGroup() {
        return userRepository.countUsersByAgeGroup();
    }
}