// ========================================================================================
// SECURITY ENTITIES
// ========================================================================================

// File: src/main/java/com/bankingsystem/entity/security/User.java
package com.bankingsystem.entity.security;

import com.bankingsystem.entity.base.AuditableEntity;
import com.bankingsystem.entity.person.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AuditableEntity {

    @NotBlank
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "is_enabled")
    private boolean enabled = true;

    @Column(name = "is_account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "is_account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "is_credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // TODO: Implement all getter and setter methods
    // TODO: Implement updateLastLogin() method
    // TODO: Implement incrementFailedAttempts() method
    // TODO: Implement resetFailedAttempts() method
    // TODO: Implement hasRole() method
    // TODO: Implement getAuthorities() method for Spring Security
}
