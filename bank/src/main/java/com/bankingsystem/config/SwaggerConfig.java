// File: src/main/java/com/bankingsystem/config/SwaggerConfig.java
package com.bankingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration for API documentation.
 * Configures API documentation, security schemes, and contact information.
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI configuration with JWT security
     */
    @Bean
    public OpenAPI customOpenAPI() {

        // TODO: Configure API documentation details
        // TODO: Setup security schemes for JWT
        // TODO: Add contact and license information
        // TODO: Configure API versioning

        return new OpenAPI()
                .info(new Info()
                        .title("Banking System REST API")
                        .version("1.0")
                        .description("Comprehensive Banking System API for account management, transactions, and customer services")
                        .termsOfService("https://bankingsystem.com/terms")
                        .contact(new Contact()
                                .name("Banking System Team")
                                .email("api-support@bankingsystem.com")
                                .url("https://bankingsystem.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // TODO: Add API versioning documentation
    // TODO: Configure environment-specific API documentation
    // TODO: Add example request/response payloads
}