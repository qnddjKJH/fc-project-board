package com.fc.fcprojectboard.config.jpa;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;   // 생성자

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;  // 수정자
}
