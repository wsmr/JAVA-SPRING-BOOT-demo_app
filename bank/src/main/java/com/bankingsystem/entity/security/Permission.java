// File: src/main/java/com/bankingsystem/entity/security/Permission.java
package com.bankingsystem.entity.security;

import com.bankingsystem.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @NotBlank
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "resource")
    private String resource;

    @Column(name = "action")
    private String action;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    // TODO: Implement all getter and setter methods
    // TODO: Implement toString() method
    // TODO: Implement equals() and hashCode() based on name
}
