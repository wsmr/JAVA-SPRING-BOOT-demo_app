// File: src/main/java/com/bankingsystem/entity/base/AuditableEntity.java
package com.bankingsystem.entity.base;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // TODO: Implement getter and setter methods
    // TODO: Setup audit configuration for user tracking
}