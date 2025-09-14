// ========================================================================================
// BASE ENTITIES
// ========================================================================================

// File: src/main/java/com/bankingsystem/entity/base/BaseEntity.java
package com.bankingsystem.entity.base;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Version
    private Long version;

    // TODO: Implement getter and setter methods
    // TODO: Implement equals and hashCode based on ID
    // TODO: Implement toString method
}