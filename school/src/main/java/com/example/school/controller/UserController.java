package com.example.school.controller;

import com.example.school.model.User;
import com.example.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Configure properly for production
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // Create Operations

    /**
     * Create a new user
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage(), "timestamp", LocalDateTime.now()));
        }
    }

    /**
     * Create multiple users
     * POST /api/users/batch
     */
    @PostMapping("/batch")
    public ResponseEntity<?> createUsers(@Valid @RequestBody List<User> users) {
        try {
            List<User> createdUsers = userService.createUsers(users);
            return new ResponseEntity<>(Map.of(
                    "message", "Successfully created " + createdUsers.size() + " users",
                    "users", createdUsers
            ), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage(), "timestamp", LocalDateTime.now()));
        }
    }

    // Read Operations

    /**
     * Get all users with optional pagination
     * GET /api/users?page=0&size=10&sort=firstName,asc
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        try {
            Sort sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<User> userPage = userService.getAllUsers(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("users", userPage.getContent());
            response.put("currentPage", userPage.getNumber());
            response.put("totalItems", userPage.getTotalElements());
            response.put("totalPages", userPage.getTotalPages());
            response.put("hasNext", userPage.hasNext());
            response.put("hasPrevious", userPage.hasPrevious());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to fetch users", "details", e.getMessage()));
        }
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get user by email
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all active users
     * GET /api/users/active?page=0&size=10
     */
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.getActiveUsers(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("users", userPage.getContent());
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // Update Operations

    /**
     * Update user
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage(), "timestamp", LocalDateTime.now()));
        }
    }

    /**
     * Activate user
     * PATCH /api/users/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable String id) {
        try {
            User user = userService.activateUser(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User activated successfully",
                    "user", user
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Deactivate user (soft delete)
     * PATCH /api/users/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable String id) {
        try {
            User user = userService.deactivateUser(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User deactivated successfully",
                    "user", user
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Delete Operations

    /**
     * Delete user by ID (hard delete)
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User deleted successfully",
                    "deletedId", id,
                    "timestamp", LocalDateTime.now()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete user by email
     * DELETE /api/users/email/{email}
     */
    @DeleteMapping("/email/{email}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        try {
            userService.deleteUserByEmail(email);
            return ResponseEntity.ok(Map.of(
                    "message", "User deleted successfully",
                    "deletedEmail", email,
                    "timestamp", LocalDateTime.now()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete all inactive users
     * DELETE /api/users/inactive
     */
    @DeleteMapping("/inactive")
    public ResponseEntity<Map<String, Object>> deleteInactiveUsers() {
        long deletedCount = userService.deleteInactiveUsers();
        return ResponseEntity.ok(Map.of(
                "message", "Inactive users deleted successfully",
                "deletedCount", deletedCount,
                "timestamp", LocalDateTime.now()
        ));
    }

    // Search Operations

    /**
     * Search users by name (first or last name)
     * GET /api/users/search?name=John&firstName=John&hobby=reading
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String hobby) {

        if (name != null && !name.trim().isEmpty()) {
            List<User> users = userService.searchUsersByName(name);
            return ResponseEntity.ok(users);
        } else if (firstName != null && !firstName.trim().isEmpty()) {
            List<User> users = userService.searchUsersByFirstName(firstName);
            return ResponseEntity.ok(users);
        } else if (hobby != null && !hobby.trim().isEmpty()) {
            List<User> users = userService.getUsersByHobby(hobby);
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.ok(userService.getAllUsers());
        }
    }

    /**
     * Get users by age range
     * GET /api/users/age-range?minAge=18&maxAge=65&page=0&size=10
     */
    @GetMapping("/age-range")
    public ResponseEntity<?> getUsersByAgeRange(
            @RequestParam @Min(0) int minAge,
            @RequestParam @Min(0) int maxAge,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        try {
            if (page >= 0 && size > 0) {
                // Paginated response
                Pageable pageable = PageRequest.of(page, size);
                Page<User> userPage = userService.getUsersByAgeRange(minAge, maxAge, pageable);

                Map<String, Object> response = new HashMap<>();
                response.put("users", userPage.getContent());
                response.put("currentPage", userPage.getNumber());
                response.put("totalItems", userPage.getTotalElements());
                response.put("totalPages", userPage.getTotalPages());

                return ResponseEntity.ok(response);
            } else {
                // Simple list response
                List<User> users = userService.getUsersByAgeRange(minAge, maxAge);
                return ResponseEntity.ok(users);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get users by hobby
     * GET /api/users/hobby?hobby=reading
     */
    @GetMapping("/hobby")
    public ResponseEntity<List<User>> getUsersByHobby(@RequestParam String hobby) {
        List<User> users = userService.getUsersByHobby(hobby);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by city
     * GET /api/users/city?city=Colombo
     */
    @GetMapping("/city")
    public ResponseEntity<List<User>> getUsersByCity(@RequestParam String city) {
        List<User> users = userService.getUsersByCity(city);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by country
     * GET /api/users/country?country=Sri Lanka
     */
    @GetMapping("/country")
    public ResponseEntity<List<User>> getUsersByCountry(@RequestParam String country) {
        List<User> users = userService.getUsersByCountry(country);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by email domain
     * GET /api/users/email-domain?domain=@gmail.com
     */
    @GetMapping("/email-domain")
    public ResponseEntity<List<User>> getUsersByEmailDomain(@RequestParam String domain) {
        List<User> users = userService.getUsersByEmailDomain(domain);
        return ResponseEntity.ok(users);
    }

    // Statistics and Analytics Endpoints

    /**
     * Get user statistics
     * GET /api/users/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getTotalUserCount());
        stats.put("activeUsers", userService.getActiveUserCount());
        stats.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(stats);
    }

    /**
     * Get total user count
     * GET /api/users/count
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getTotalUserCount() {
        long count = userService.getTotalUserCount();
        return ResponseEntity.ok(Map.of("totalUsers", count));
    }

    /**
     * Get active user count
     * GET /api/users/count/active
     */
    @GetMapping("/count/active")
    public ResponseEntity<Map<String, Object>> getActiveUserCount() {
        long count = userService.getActiveUserCount();
        return ResponseEntity.ok(Map.of("activeUsers", count));
    }

    /**
     * Check if user exists by email
     * GET /api/users/exists?email=john@example.com
     */
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Object>> userExists(@RequestParam String email) {
        boolean exists = userService.userExistsByEmail(email);
        return ResponseEntity.ok(Map.of(
                "email", email,
                "exists", exists
        ));
    }

    /**
     * Get most popular hobbies
     * GET /api/users/analytics/hobbies
     */
    @GetMapping("/analytics/hobbies")
    public ResponseEntity<List<Object>> getMostPopularHobbies() {
        List<Object> hobbies = userService.getMostPopularHobbies();
        return ResponseEntity.ok(hobbies);
    }

    /**
     * Get average age by city
     * GET /api/users/analytics/age-by-city
     */
    @GetMapping("/analytics/age-by-city")
    public ResponseEntity<List<Object>> getAverageAgeByCity() {
        List<Object> data = userService.getAverageAgeByCity();
        return ResponseEntity.ok(data);
    }

    /**
     * Get user count by age groups
     * GET /api/users/analytics/age-groups
     */
    @GetMapping("/analytics/age-groups")
    public ResponseEntity<List<Object>> getUserCountByAgeGroup() {
        List<Object> data = userService.getUserCountByAgeGroup();
        return ResponseEntity.ok(data);
    }

//    /**
//     * Get average age by city
//     * GET /api/users/analytics/average-age-by-city
//     */
//    @GetMapping("/analytics/average-age-by-city")
//    public ResponseEntity<List<Object>> getAverageAgeByCity() {
//        List<Object> avgAges = userService.getAverageAgeByCity();
//        return ResponseEntity.ok(avgAges);
//    }
//    /**
//     * Get user count by age group
//     * GET /api/users/analytics/age-groups
//     */
//    @GetMapping("/analytics/age-groups")
//    public ResponseEntity<List<Object>> getUserCountByAgeGroup() {
//        List<Object> ageGroups = userService.getUserCountByAgeGroup();
//        return ResponseEntity.ok(ageGroups);
//    }
}