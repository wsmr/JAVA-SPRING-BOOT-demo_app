This comprehensive guide covers everything from basic setup to advanced production deployment. You now have a complete, production-ready Spring Boot application with MongoDB integration that includes proper error handling, validation, testing, and deployment strategies.# Complete MongoDB Atlas Setup Guide for Spring Boot Applications - Full Implementation

# Complete MongoDB Atlas Setup Guide for Spring Boot Applications - Full Implementation

## Table of Contents
1. [Understanding MongoDB Architecture](#understanding-mongodb-architecture)
2. [MongoDB Deployment Options](#mongodb-deployment-options)
3. [Setting Up MongoDB Atlas (Cloud)](#setting-up-mongodb-atlas-cloud)
4. [Setting Up Local MongoDB](#setting-up-local-mongodb)
5. [MongoDB Compass Installation](#mongodb-compass-installation)
6. [Spring Boot Project Setup](#spring-boot-project-setup)
7. [Spring Boot MongoDB Configuration](#spring-boot-mongodb-configuration)
8. [Creating MongoDB Entities](#creating-mongodb-entities)
9. [Repository Layer Implementation](#repository-layer-implementation)
10. [Service Layer Implementation](#service-layer-implementation)
11. [REST Controller Implementation](#rest-controller-implementation)
12. [Main Application Configuration](#main-application-configuration)
13. [Testing Your Application](#testing-your-application)
14. [Understanding IntelliJ/IDE Setup](#understanding-intellijide-setup)
15. [Troubleshooting Guide](#troubleshooting-guide)
16. [Advanced Features and Next Steps](#advanced-features-and-next-steps)
17. [Production Deployment](#production-deployment)

## Understanding MongoDB Architecture

### What is MongoDB?
MongoDB is a NoSQL database that stores data in flexible, JSON-like documents instead of rows and columns like traditional SQL databases. This flexibility makes it ideal for modern applications that need to handle diverse data structures.

### Key Concepts and Hierarchy
```
MongoDB Instance (Server)
├── Database 1 (e.g., "ecommerce")
│   ├── Collection 1 (e.g., "users")
│   │   ├── Document 1 (user record)
│   │   ├── Document 2 (user record)
│   │   └── Document 3 (user record)
│   ├── Collection 2 (e.g., "products")
│   └── Collection 3 (e.g., "orders")
├── Database 2 (e.g., "blog")
│   ├── Collection 1 (e.g., "posts")
│   └── Collection 2 (e.g., "comments")
```

**Terminology Mapping:**
- **Instance/Server**: The MongoDB server running somewhere (equivalent to SQL Server instance)
- **Database**: A container that groups related collections (like a schema in SQL)
- **Collection**: Similar to a table in SQL - stores related documents
- **Document**: Individual records (like rows in SQL, but with flexible structure)

### MongoDB vs SQL Comparison
| SQL Database | MongoDB |
|--------------|---------|
| Database | Database |
| Table | Collection |
| Row | Document |
| Column | Field |
| Index | Index |
| Table joins | Embedded documents |

## MongoDB Deployment Options

### Option A: MongoDB Atlas (Cloud - Recommended for Beginners)
**Advantages:**
- ✅ Hosted by MongoDB in the cloud
- ✅ No installation needed
- ✅ Automatic backups, scaling, security
- ✅ Free tier available (512MB storage)
- ✅ Built-in monitoring and performance insights
- ✅ Automatic OS and security updates

**Best for:** Production applications, team development, beginners

### Option B: Local MongoDB Installation
**Advantages:**
- ✅ Install MongoDB on your computer
- ✅ Full control over configuration
- ✅ No internet dependency for development
- ✅ Good for learning and experimentation

**Best for:** Development, learning, offline work

### Option C: Docker (Advanced)
**Advantages:**
- ✅ Run MongoDB in a container
- ✅ Easy to start/stop/reset
- ✅ Consistent environment across different machines
- ✅ Easy to manage multiple versions

**Best for:** Advanced developers, CI/CD pipelines

## Setting Up MongoDB Atlas (Cloud)

### Step 1: Create Atlas Account
1. Navigate to [https://www.mongodb.com/atlas](https://www.mongodb.com/atlas)
2. Click "Try Free" or "Start Free"
3. Fill out registration form:
   - First Name, Last Name
   - Email address
   - Company (optional)
   - Create strong password
4. Verify your email address
5. Complete profile setup

### Step 2: Create Your First Cluster
1. **Choose Deployment Type:**
   - Click "Create" → "Database"
   - Select "Free" tier (M0 Sandbox)

2. **Configure Cluster Settings:**
   - **Provider Selection** (for Sri Lanka/Colombo users):
     - Choose **AWS** (recommended for best performance)
     - Alternative: Google Cloud or Microsoft Azure
   - **Region Selection:**
     - Choose **Mumbai (ap-south-1)** for optimal latency from Sri Lanka
     - Expected latency: 15-30ms from Colombo
   - **Cluster Name:**
     - Enter meaningful name: `diyawanna-sup-cluster-0`
     - Note: Cannot be changed after creation

3. **Advanced Configuration (Optional):**
   ```
   Cluster Name: diyawanna-sup-cluster-0
   Provider: AWS
   Region: Mumbai (ap-south-1)
   Tier: M0 Sandbox (Free)
   Storage: 512 MB
   ```

4. **Tagging (Recommended):**
   ```
   Key: Environment    Value: Development
   Key: Project        Value: DiyawannaSup
   Key: Purpose        Value: SpringBootApp
   Key: Owner          Value: [Your Name]
   ```

5. Click "Create Deployment" and wait 3-5 minutes for cluster creation

### Step 3: Set Up Database Access (User Management)
1. **Navigate to Database Access:**
   - Go to "Database Access" in left sidebar
   - Click "Add New Database User"

2. **Configure User Authentication:**
   - **Authentication Method**: Choose "Password"
   - **Username**: `diyawanna-user` (or your preferred username)
   - **Password**: 
     - Option 1: Click "Autogenerate Secure Password" (recommended)
     - Option 2: Create custom password (minimum 8 characters)
   - **⚠️ CRITICAL**: Copy and save password immediately - you won't see it again!

3. **Set Database User Privileges:**
   - Choose "Built-in Role"
   - Select "Read and write to any database" (for development)
   - For production: Use more restrictive permissions

4. **Restrict Access (Optional):**
   - Leave "Restrict Access to Specific Clusters/Federated Database Instances" unchecked for simplicity

5. Click "Add User"

### Step 4: Set Up Network Access (IP Whitelist)
1. **Navigate to Network Access:**
   - Go to "Network Access" in left sidebar
   - Click "Add IP Address"

2. **Configure IP Access:**
   **For Development:**
   - Click "Allow Access from Anywhere" (0.0.0.0/0)
   - This allows access from any IP address
   - ⚠️ **Warning**: Only use this for development environments

   **For Production:**
   - Click "Add Current IP Address"
   - Add specific IP addresses of your production servers
   - More secure approach

3. **IP Address Configuration:**
   ```
   Current IP: 103.247.48.195 (Automatically detected for Colombo)
   Description: Development Machine - Colombo
   ```

4. Click "Confirm"

### Step 5: Get Connection String
1. **Navigate to Database:**
   - Go to "Database" in left sidebar
   - Find your cluster
   - Click "Connect" button

2. **Choose Connection Method:**
   - Select "Connect your application"
   - Choose "Java" as driver
   - Select version "4.3 or later"

3. **Copy Connection String:**
   ```
   mongodb+srv://diyawanna-user:<password>@diyawanna-sup-cluster-0.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```

4. **Customize Connection String:**
   - Replace `<password>` with your actual password
   - Add database name: `diyawanna_sup_db`
   - Final format:
   ```
   mongodb+srv://diyawanna-user:YOUR_PASSWORD@diyawanna-sup-cluster-0.xxxxx.mongodb.net/diyawanna_sup_db?retryWrites=true&w=majority
   ```

## Setting Up Local MongoDB

### Step 1: Install MongoDB Community Edition

#### On macOS:
```bash
# Install using Homebrew
brew tap mongodb/brew
brew install mongodb-community

# Start MongoDB service
brew services start mongodb/brew/mongodb-community

# Stop MongoDB service (when needed)
brew services stop mongodb/brew/mongodb-community
```

#### On Windows:
1. **Download MongoDB:**
   - Go to [mongodb.com](https://www.mongodb.com/try/download/community)
   - Download MongoDB Community Server
   - Choose Windows x64 version

2. **Installation Steps:**
   - Run the installer (.msi file)
   - Choose "Complete" installation
   - Select "Install MongoD as a Service"
   - Choose "Run service as Network Service user"
   - Install MongoDB Compass (GUI tool) - check the box

3. **Verify Installation:**
   - Open Command Prompt as Administrator
   - Type `mongod --version` to verify installation

#### On Linux (Ubuntu/Debian):
```bash
# Import MongoDB public GPG key
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | sudo apt-key add -

# Add MongoDB repository
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list

# Update package database
sudo apt-get update

# Install MongoDB
sudo apt-get install -y mongodb-org

# Start MongoDB service
sudo systemctl start mongod

# Enable MongoDB to start on boot
sudo systemctl enable mongod

# Check MongoDB status
sudo systemctl status mongod
```

### Step 2: Verify Local MongoDB Installation
```bash
# Connect to MongoDB shell
mongosh

# You should see MongoDB shell prompt
test> 

# Show databases
show dbs

# Create and use a database
use diyawanna_sup_db

# Exit MongoDB shell
exit
```

## MongoDB Compass Installation

### Step 1: Download and Install
1. **Download Compass:**
   - Visit [mongodb.com/products/compass](https://www.mongodb.com/products/compass)
   - Download for your operating system
   - Install the application

### Step 2: Connect to Your Database

#### Connect to MongoDB Atlas:
```
Connection String: mongodb+srv://diyawanna-user:PASSWORD@diyawanna-sup-cluster-0.xxxxx.mongodb.net/
```

#### Connect to Local MongoDB:
```
Connection String: mongodb://localhost:27017
Host: localhost
Port: 27017
```

### Step 3: Compass Interface Overview
- **Databases**: View all databases in your MongoDB instance
- **Collections**: Browse collections within databases
- **Documents**: View and edit individual documents
- **Schema**: Analyze data structure and types
- **Indexes**: Manage database indexes for performance

## Spring Boot Project Setup

### Step 1: Create Project Structure
Using Spring Initializr (start.spring.io) or your IDE:

**Project Configuration:**
```
Project: Maven
Language: Java
Spring Boot: 3.2.x (latest stable)
Packaging: Jar
Java: 17 or 21
Group: com.example
Artifact: diyawanna-sup
Name: Diyawanna Sup
Description: Spring Boot application with MongoDB
Package name: com.example.diyawanna
```

**Required Dependencies:**
- Spring Web
- Spring Data MongoDB
- Spring Boot DevTools (for development)
- Spring Boot Starter Test (for testing)

### Step 2: Project Directory Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/example/diyawanna/
│   │       ├── DiyawannaApplication.java
│   │       ├── controller/
│   │       │   └── UserController.java
│   │       ├── model/
│   │       │   └── User.java
│   │       ├── repository/
│   │       │   └── UserRepository.java
│   │       ├── service/
│   │       │   └── UserService.java
│   │       └── config/
│   │           └── MongoConfig.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-prod.properties
│       └── static/
└── test/
    └── java/
        └── com/example/diyawanna/
```

### Step 3: Maven Dependencies (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Spring Boot MongoDB Configuration

### Configuration for MongoDB Atlas
**application.properties:**
```properties
# MongoDB Atlas Configuration
spring.data.mongodb.uri=mongodb+srv://diyawanna-user:YOUR_PASSWORD@diyawanna-sup-cluster-0.xxxxx.mongodb.net/diyawanna_sup_db?retryWrites=true&w=majority

# Application settings
server.port=8080
spring.application.name=diyawanna-sup-app

# MongoDB specific settings
spring.data.mongodb.option.max-connection-pool-size=20
spring.data.mongodb.option.min-connection-pool-size=5
spring.data.mongodb.option.max-connection-idle-time=30000

# Logging configuration
logging.level.org.springframework.data.mongodb=INFO
logging.level.com.example.diyawanna=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

### Configuration for Local MongoDB
**application-dev.properties:**
```properties
# Local MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=diyawanna_sup_db

# Alternative: Use URI format for local
# spring.data.mongodb.uri=mongodb://localhost:27017/diyawanna_sup_db

# Application settings
server.port=8080
spring.application.name=diyawanna-sup-app

# Development specific settings
spring.devtools.restart.enabled=true
logging.level.org.springframework.data.mongodb=DEBUG
```

### Environment Variables (Production Ready)
**application-prod.properties:**
```properties
# Production MongoDB Configuration using environment variables
spring.data.mongodb.uri=${MONGODB_URI}
spring.data.mongodb.database=${MONGODB_DATABASE:diyawanna_sup_prod}

# Production settings
server.port=${PORT:8080}
spring.application.name=diyawanna-sup-app

# Security settings
spring.data.mongodb.option.ssl.enabled=true
spring.data.mongodb.option.ssl.invalid-hostname-allowed=false

# Connection pool optimization
spring.data.mongodb.option.max-connection-pool-size=50
spring.data.mongodb.option.min-connection-pool-size=10
spring.data.mongodb.option.max-connection-idle-time=60000
spring.data.mongodb.option.max-wait-time=10000

# Logging for production
logging.level.org.springframework.data.mongodb=WARN
logging.level.com.example.diyawanna=INFO
```

**Set Environment Variables:**
```bash
# Linux/macOS
export MONGODB_URI="mongodb+srv://username:password@cluster.mongodb.net/database"
export MONGODB_DATABASE="diyawanna_sup_prod"

# Windows Command Prompt
set MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/database
set MONGODB_DATABASE=diyawanna_sup_prod

# Windows PowerShell
$env:MONGODB_URI="mongodb+srv://username:password@cluster.mongodb.net/database"
$env:MONGODB_DATABASE="diyawanna_sup_prod"
```

## Creating MongoDB Entities

### User Entity (Complete Implementation)
```java
package com.example.diyawanna.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.*;
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
```

### Address Entity (Embedded Document)
```java
package com.example.diyawanna.model;

import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Address {
    
    @Field("street")
    @NotBlank(message = "Street address is required")
    private String street;
    
    @Field("city")
    @NotBlank(message = "City is required")
    private String city;
    
    @Field("state_province")
    private String stateProvince;
    
    @Field("postal_code")
    @Size(min = 5, max = 10, message = "Postal code must be between 5 and 10 characters")
    private String postalCode;
    
    @Field("country")
    @NotBlank(message = "Country is required")
    private String country;
    
    // Constructors
    public Address() {}
    
    public Address(String street, String city, String stateProvince, 
                   String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.stateProvince = stateProvince;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    // Getters and Setters
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getStateProvince() {
        return stateProvince;
    }
    
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
```

## Repository Layer Implementation

### UserRepository Interface (Complete Implementation)
```java
package com.example.diyawanna.repository;

import com.example.diyawanna.model.User;
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




    ## Service Layer Implementation

### UserService Class (Complete Business Logic)
```java
package com.example.diyawanna.service;

import com.example.diyawanna.model.User;
import com.example.diyawanna.repository.UserRepository;
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
```

## REST Controller Implementation

### UserController Class (Complete REST API)
```java
package com.example.diyawanna.controller;

import com.example.diyawanna.model.User;
import com.example.diyawanna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
}
```

## Main Application Configuration

### DiyawannaApplication.java (Main Class)
```java
package com.example.diyawanna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.diyawanna.repository")
@EnableMongoAuditing
public class DiyawannaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiyawannaApplication.class, args);
        
        System.out.println("\n🌟 ================================");
        System.out.println("🚀 Diyawanna Application Started Successfully!");
        System.out.println("📊 MongoDB connection configured");
        System.out.println("🔗 API available at: http://localhost:8080");
        System.out.println("📋 Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("================================");
        
        System.out.println("\n📍 Available Endpoints:");
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                    USER OPERATIONS                     │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ GET    /api/users                - Get all users       │");
        System.out.println("│ POST   /api/users                - Create new user     │");
        System.out.println("│ GET    /api/users/{id}           - Get user by ID      │");
        System.out.println("│ PUT    /api/users/{id}           - Update user         │");
        System.out.println("│ DELETE /api/users/{id}           - Delete user         │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│                    SEARCH OPERATIONS                   │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ GET    /api/users/search?name=John  - Search by name   │");
        System.out.println("│ GET    /api/users/age-range?min=18&max=65 - Age range  │");
        System.out.println("│ GET    /api/users/hobby?hobby=reading    - By hobby    │");
        System.out.println("│ GET    /api/users/city?city=Colombo     - By city      │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│                    STATISTICS                          │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ GET    /api/users/count          - Get total count     │");
        System.out.println("│ GET    /api/users/stats          - Get user statistics │");
        System.out.println("│ GET    /api/users/analytics/hobbies - Popular hobbies  │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        System.out.println("\n🔧 Development Tips:");
        System.out.println("• Use MongoDB Compass to view your data visually");
        System.out.println("• Check application-dev.properties for local development");
        System.out.println("• Use Postman or cURL to test API endpoints");
        System.out.println("• Monitor logs for MongoDB connection status");
        System.out.println("\n✅ Application is ready for development!\n");
    }
}
```

### MongoConfig.java (Optional Configuration)
```java
package com.example.diyawanna.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "diyawanna_sup_db";
    }

    /**
     * Enable JSR-303 validation for MongoDB documents
     */
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Custom type conversions (if needed)
     */
    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }

    /**
     * Connection configuration
     * This is handled automatically by Spring Boot's auto-configuration
     * when using application.properties
     */
}
```

## Testing Your Application

### Step 1: Start Your Application
```bash
# Navigate to your project directory
cd /path/to/your/diyawanna-project

# Run with Maven
./mvnw spring-boot:run

# Or with Maven Wrapper (Windows)
mvnw.cmd spring-boot:run

# Run with Gradle
./gradlew bootRun

# Or run from IDE
# Right-click on DiyawannaApplication.java → Run
```

### Step 2: Verify Application Startup
Look for these log messages:
```
🌟 Diyawanna Application Started Successfully!
📊 MongoDB connection configured
🚀 API available at: http://localhost:8080
```

### Step 3: Test with cURL Commands

#### Create a User (POST)
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "age": 25,
    "phoneNumber": "+94771234567",
    "hobbies": ["reading", "swimming", "coding"],
    "address": {
      "street": "123 Main Street",
      "city": "Colombo",
      "stateProvince": "Western",
      "postalCode": "00100",
      "country": "Sri Lanka"
    }
  }'
```

#### Get All Users (GET)
```bash
curl http://localhost:8080/api/users
```

#### Get User by ID (GET)
```bash
curl http://localhost:8080/api/users/{user-id}
```

#### Search Users by Name
```bash
curl "http://localhost:8080/api/users/search?name=John"
```

#### Get Users by Age Range
```bash
curl "http://localhost:8080/api/users/age-range?minAge=20&maxAge=30"
```

#### Get User Statistics
```bash
curl http://localhost:8080/api/users/stats
```

#### Get User Count
```bash
curl http://localhost:8080/api/users/count
```

### Step 4: Test with Postman

#### Postman Collection Setup
1. **Create New Collection**: "Diyawanna API"
2. **Set Base URL**: `http://localhost:8080`
3. **Add Environment Variables**:
   - `base_url`: `http://localhost:8080`
   - `user_id`: (will be set after creating a user)

#### Sample Postman Requests

**Create User Request:**
```
Method: POST
URL: {{base_url}}/api/users
Headers: Content-Type: application/json
Body:
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@gmail.com",
  "age": 28,
  "phoneNumber": "+94771234568",
  "hobbies": ["photography", "traveling", "cooking"],
  "address": {
    "street": "456 Temple Street",
    "city": "Kandy",
    "stateProvince": "Central",
    "postalCode": "20000",
    "country": "Sri Lanka"
  }
}
```

**Get All Users Request:**
```
Method: GET
URL: {{base_url}}/api/users
Query Parameters:
- page: 0
- size: 10
- sortBy: firstName
- sortDir: asc
```

## Understanding IntelliJ/IDE Setup

### Your Current MongoDB Configuration
Based on your screenshot showing MongoDB Compass configuration:

```
Host: localhost
Port: 27017
Database: diyawanna_sup_main_dev
Connection Type: Default
Driver: MongoDB
```

This configuration is perfect for local development. Here's what each part means:

### MongoDB Connection Details
- **Host**: `localhost` - MongoDB is running on your local machine
- **Port**: `27017` - Default MongoDB port
- **Database**: `diyawanna_sup_main_dev` - Your application database
- **Collections**: Will be created automatically when you insert documents

### IntelliJ Database Tool Window Setup
1. **Add MongoDB Data Source**:
   - Go to View → Tool Windows → Database
   - Click "+" → Data Source → MongoDB
   - Configure connection:
     ```
     Host: localhost
     Port: 27017
     Database: diyawanna_sup_main_dev
     ```

2. **MongoDB Plugin for IntelliJ**:
   - Install "Mongo Plugin" from JetBrains Marketplace
   - Provides MongoDB query console and document viewing

### IDE Configuration Files
Your project should have these configuration files:

**application-dev.properties** (for local development):
```properties
# Local MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=diyawanna_sup_main_dev

# Development settings
spring.profiles.active=dev
server.port=8080
logging.level.com.example.diyawanna=DEBUG
```

**Run Configuration in IntelliJ**:
- Main Class: `com.example.diyawanna.DiyawannaApplication`
- VM Options: `-Dspring.profiles.active=dev`
- Program Arguments: (none needed)
- Working Directory: Your project root

## Troubleshooting Guide

### Issue 1: Connection Refused
**Problem**: Application can't connect to MongoDB
```
com.mongodb.MongoSocketException: localhost:27017
```

**Solutions**:
1. **Check MongoDB Service**:
   ```bash
   # macOS with Homebrew
   brew services list | grep mongodb
   brew services start mongodb/brew/mongodb-community
   
   # Windows
   net start MongoDB
   
   # Linux
   sudo systemctl status mongod
   sudo systemctl start mongod
   ```

2. **Verify MongoDB is Running**:
   ```bash
   mongosh
   # Should connect successfully
   ```

3. **Check Port Availability**:
   ```bash
   netstat -an | grep 27017
   # Should show MongoDB listening on port 27017
   ```

### Issue 2: Authentication Failed (Atlas)
**Problem**: Wrong credentials for MongoDB Atlas
```
MongoSecurityException: Exception authenticating MongoCredential
```

**Solutions**:
1. **Verify Connection String**:
   - Check username and password are correct
   - Ensure special characters in password are URL-encoded
   - Verify cluster name is correct

2. **Check Database User Permissions**:
   - Go to MongoDB Atlas → Database Access
   - Verify user has "Read and write to any database" permission
   - Check if user is restricted to specific clusters

3. **Network Access**:
   - Go to MongoDB Atlas → Network Access
   - Ensure your IP address is whitelisted
   - For development, use 0.0.0.0/0 (not for production)

### Issue 3: Database/Collection Not Created
**Problem**: No data visible in MongoDB Compass
```
Collections are empty or not showing
```

**Solutions**:
1. **Collections Creation**:
   - MongoDB creates collections automatically when first document is inserted
   - Try creating a user through your API
   - Check if the API request was successful

2. **Database Name Mismatch**:
   ```properties
   # Ensure database names match
   spring.data.mongodb.database=diyawanna_sup_main_dev
   ```

3. **Check Application Logs**:
   ```bash
   # Look for MongoDB connection logs
   tail -f logs/spring.log | grep -i mongo
   ```

### Issue 4: Validation Errors
**Problem**: Bean validation not working
```
@Valid annotations not triggering validation
```

**Solutions**:
1. **Add Validation Dependency**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-validation</artifactId>
   </dependency>
   ```

2. **Enable Validation**:
   ```java
   @RestController
   @Validated  // Add this annotation
   public class UserController {
       @PostMapping
       public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
           // Method implementation
       }
   }
   ```

### Issue 5: Circular Reference Error
**Problem**: Bean creation error
```
BeanCurrentlyInCreationException: Circular reference
```

**Solutions**:
1. **Check Configuration Classes**:
   ```java
   @Configuration
   public class MongoConfig {
       // Remove @Autowired dependencies between config beans
       // Use @Bean methods instead
   }
   ```

2. **Use Lazy Initialization**:
   ```java
   @Autowired
   @Lazy
   private UserRepository userRepository;
   ```

### Issue 6: Performance Issues
**Problem**: Slow query performance

**Solutions**:
1. **Add Indexes**:
   ```java
   @Document(collection = "users")
   @CompoundIndex(def = "{'email': 1, 'isActive': 1}")
   public class User {
       @Indexed(unique = true)
       private String email;
   }
   ```

2. **Optimize Queries**:
   ```java
   // Use pagination for large result sets
   Page<User> findByIsActiveTrue(Pageable pageable);
   
   // Use projections to limit returned fields
   @Query(value = "{ 'isActive': true }", fields = "{ 'firstName': 1, 'lastName': 1, 'email': 1 }")
   List<User> findActiveUsersBasicInfo();
   ```

3. **Connection Pool Configuration**:
   ```properties
   spring.data.mongodb.option.max-connection-pool-size=50
   spring.data.mongodb.option.min-connection-pool-size=10
   spring.data.mongodb.option.max-connection-idle-time=60000
   ```

### Issue 7: SSL/TLS Issues (Atlas)
**Problem**: SSL handshake failure
```
SSLHandshakeException: sun.security.validator.ValidatorException
```

**Solutions**:
1. **Update Java Version**:
   - Use Java 8u151+ or Java 11+
   - Older versions have SSL compatibility issues

2. **Add SSL Parameters**:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://user:pass@cluster.mongodb.net/db?ssl=true&retryWrites=true&w=majority
   ```

## Advanced Features and Next Steps

### 1. Data Validation Enhancement
```java
@Document(collection = "users")
public class User {
    
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;
    
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 150, message = "Age must be realistic")
    private int age;
}
```

### 2. Global Exception Handling
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Validation failed", "details", ex.getMessage()));
    }
    
    @ExceptionHandler(MongoException.class)
    public ResponseEntity<?> handleMongo(MongoException ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of("error", "Database error", "message", ex.getMessage()));
    }
}
```

### 3. API Documentation with Swagger
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.2</version>
</dependency>
```

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        // Implementation
    }
}
```

### 4. Unit Testing
```java
@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.uri=mongodb://localhost:27017/test_db"
})
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    void shouldCreateUser() {
        User user = new User("John", "Doe", "john@example.com", 25);
        User created = userService.createUser(user);
        
        assertThat(created.getId()).isNotNull();
        assertThat(created.getEmail()).isEqualTo("john@example.com");
    }
}
```

### 5. Security Implementation
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/users/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}
```

### 6. Caching Implementation
```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users", "userStats");
    }
}

@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#email")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @CacheEvict(value = "users", key = "#user.email")
    public User updateUser(String id, User user) {
        // Update logic
    }
}
```

## Production Deployment

### Environment Configuration
```properties
# application-prod.properties
spring.data.mongodb.uri=${MONGODB_URI}
spring.data.mongodb.option.ssl.enabled=true
spring.data.mongodb.option.max-connection-pool-size=100
spring.data.mongodb.option.min-connection-pool-size=20

# Security settings
server.port=${PORT:8080}
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=when-authorized

# Logging
logging.level.com.example.diyawanna=INFO
logging.level.org.springframework.data.mongodb=WARN
```

### Docker Configuration
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/diyawanna-sup-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod
ENV MONGODB_URI=${MONGODB_URI}

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Monitoring and Health Checks
```java
@Component
public class MongoHealthIndicator implements HealthIndicator {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public Health health() {
        try {
            mongoTemplate.getCollection("users").estimatedDocumentCount();
            return Health.up()
                    .withDetail("database", "MongoDB")
                    .withDetail("status", "Connected")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "MongoDB")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
```






