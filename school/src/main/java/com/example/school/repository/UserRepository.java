package com.example.school.repository;

import com.example.school.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Basic Query Methods (Spring Data MongoDB auto-implementation)

    // Find by email (exact match)
    Optional<User> findByEmail(String email);

    // Find by first name (case insensitive)
    List<User> findByFirstNameIgnoreCase(String firstName);

    // Find by first name and last name
    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    // Find by age range
    List<User> findByAgeBetween(int minAge, int maxAge);

    // Find by age greater than
    List<User> findByAgeGreaterThan(int age);

    // Find by age less than
    List<User> findByAgeLessThan(int age);

    // Find users with specific hobby
    List<User> findByHobbiesContaining(String hobby);

    // Find active users
    List<User> findByIsActiveTrue();

    // Find inactive users
    List<User> findByIsActiveFalse();

    // count by active false
    long countByIsActiveFalse();

//    // Find by phone number (exact match)
//    Optional<User> findByPhoneNumber(String phoneNumber);
//    // Find by phone number (case insensitive)
//    List<User> findByPhoneNumberIgnoreCase(String phoneNumber);
//    // Find by phone number pattern (regex)
//    List<User> findByPhoneNumberRegex(String pattern);
//    // Find by created date after
//    List<User> findByCreatedAtAfter(LocalDateTime date);
//    // Find by updated date before
//    List<User> findByUpdatedAtBefore(LocalDateTime date);
//    // Find by created date between
//    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
//    // Find by updated date between
//    List<User> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
//    // Find by first name or last name (case insensitive)
//    List<User> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
//    // Find by first name and age greater than
//    List<User> findByFirstNameAndAgeGreaterThan(String firstName, int age);
//    // Find by last name and age less than
//    List<User> findByLastNameAndAgeLessThan(String lastName, int age);
//    // Find by first name and last name or email
//    List<User> findByFirstNameAndLastNameOrEmail(String firstName, String lastName, String email);
//    // Find by hobbies containing all specified hobbies
//    List<User> findByHobbiesContainingAll(List<String> hobbies);

//    @Query("{ 'hobbies' : { $all : ?0 } }")
//    List<User> findByHobbiesContainingAll(List<String> hobbies);

//    // Find by hobbies containing any specified hobby
//    List<User> findByHobbiesContainingAny(List<String> hobbies);
//    // Find by address (embedded document query)
//    List<User> findByAddress(Address address);
//    // Find by street (embedded document query)
    @Query("{ 'address.street' : ?0 }")
    List<User> findByStreet(String street);



    // Find by city (embedded document query)
    @Query("{ 'address.city' : ?0 }")
    List<User> findByCity(String city);

    // Find by country (embedded document query)
    @Query("{ 'address.country' : ?0 }")
    List<User> findByCountry(String country);

    // Custom Query Methods using @Query annotation

    // Find by email domain
    @Query("{ 'email' : { $regex: ?0, $options: 'i' } }")
    List<User> findByEmailDomain(String domain);

    // Find users by first name and minimum age
    @Query("{ 'first_name' : ?0, 'age' : { $gte: ?1 } }")
    List<User> findByFirstNameAndMinAge(String firstName, int minAge);

    // Find users created after specific date
    @Query("{ 'created_at' : { $gte: ?0 } }")
    List<User> findUsersCreatedAfter(LocalDateTime date);

    // Find users by multiple hobbies
    @Query("{ 'hobbies' : { $all: ?0 } }")
    List<User> findByAllHobbies(List<String> hobbies);

    // Find users by any of the specified hobbies
    @Query("{ 'hobbies' : { $in: ?0 } }")
    List<User> findByAnyHobby(List<String> hobbies);

    // Find users with phone number pattern
    @Query("{ 'phone_number' : { $regex: ?0 } }")
    List<User> findByPhonePattern(String pattern);

    // Search users by name (first name or last name)
    @Query("{ $or: [ " +
            "  { 'first_name': { $regex: ?0, $options: 'i' } }, " +
            "  { 'last_name': { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<User> searchByName(String name);

    // Aggregation Queries using @Aggregation annotation

    // Count users by age group
    @Aggregation(pipeline = {
            "{ $group: { _id: { $floor: { $divide: ['$age', 10] } }, count: { $sum: 1 } } }",
            "{ $sort: { _id: 1 } }"
    })
    List<Object> countUsersByAgeGroup();

    // Get average age by city
    @Aggregation(pipeline = {
            "{ $group: { _id: '$address.city', avgAge: { $avg: '$age' }, count: { $sum: 1 } } }",
            "{ $sort: { avgAge: -1 } }"
    })
    List<Object> getAverageAgeByCity();

    // Find most popular hobbies
    @Aggregation(pipeline = {
            "{ $unwind: '$hobbies' }",
            "{ $group: { _id: '$hobbies', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }",
            "{ $limit: 10 }"
    })
    List<Object> getMostPopularHobbies();

    // Existence and Count Methods

    // Check if user exists by email
    boolean existsByEmail(String email);

    // Check if user exists by phone number
    boolean existsByPhoneNumber(String phoneNumber);

    // Count users by age range
    long countByAgeBetween(int minAge, int maxAge);

    // Count active users
    long countByIsActiveTrue();

    // Count users by city
    @Query(value = "{ 'address.city' : ?0 }", count = true)
    long countByCity(String city);

    // Pagination Methods

    // Find all users with pagination
    Page<User> findAll(Pageable pageable);

    // Find users by age range with pagination
    Page<User> findByAgeBetween(int minAge, int maxAge, Pageable pageable);

    // Find active users with pagination
    Page<User> findByIsActiveTrue(Pageable pageable);

    // Find users by city with pagination
    @Query("{ 'address.city' : ?0 }")
    Page<User> findByCityWithPagination(String city, Pageable pageable);

    // Delete Methods

    // Delete by email
    void deleteByEmail(String email);

    // Delete inactive users
    void deleteByIsActiveFalse();

    // Custom delete by age
    @Query(value = "{ 'age' : { $lt: ?0 } }", delete = true)
    void deleteUsersYoungerThan(int age);
}
