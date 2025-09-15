// ========================================================================================
// MAIN APPLICATION FILE (FULLY IMPLEMENTED)
// ========================================================================================

// File: src/main/java/com/bankingsystem/BankingSystemApplication.java
package com.bankingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Spring Boot application class.
 * Entry point for the Banking System application.
 *
 * Enables:
 * - JPA Auditing for automatic entity timestamps
 * - Caching for improved performance
 * - Asynchronous processing capabilities
 * - Scheduled task execution
 * - Transaction management
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class BankingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
        System.out.println("🏦 Banking System Application Started Successfully!");
        System.out.println("📱 Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("⚡ Actuator Health: http://localhost:8080/actuator/health");
        System.out.println("🎯 API Base URL: http://localhost:8080/api/v1");
    }
}