package com.example.school.config;

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
//    @Bean
//    @Override
//    public MongoClient mongoClient() {
//        // MongoClient is automatically configured by Spring Boot
//        // No need to implement this method unless you have custom settings
//        return super.mongoClient();
//    }
}