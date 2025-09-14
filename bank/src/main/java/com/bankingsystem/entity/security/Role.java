// File: src/main/java/com/bankingsystem/entity/security/Role.java
package com.bankingsystem.entity.security;

import com.bankingsystem.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @NotBlank
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    // TODO: Implement all getter and setter methods
    // TODO: Implement hasPermission() method
    // TODO: Implement addPermission() method
    // TODO: Implement removePermission() method
}