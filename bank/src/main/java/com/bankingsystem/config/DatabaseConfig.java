// ========================================================================================
// CONFIGURATION FILES
// ========================================================================================

// File: src/main/java/com/bankingsystem/config/DatabaseConfig.java
package com.bankingsystem.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Database configuration class.
 * Configures connection pooling, JPA repositories, and database-specific settings.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.bankingsystem.repository")
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * Primary DataSource bean with HikariCP connection pooling
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);

        // Connection pool settings
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(60000);

        return new HikariDataSource(config);
    }

    // TODO: Configure read-only datasource for reporting queries
    // TODO: Setup database health check indicators
    // TODO: Configure database metrics collection

    // TODO: Configure database connection pool settings
    // TODO: Configure JPA properties for production optimization
    // TODO: Setup read/write database separation if needed
    // TODO: Configure database health check endpoints
}