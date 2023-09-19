package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaBaseTimeEntity {
    @Column(updatable = false, name = "create_datetime")
    private LocalDateTime createDatetime;
    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createDatetime = this.updateDatetime = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDatetime = LocalDateTime.now();
    }

    public void forceUpdate() {
        this.updateDatetime = LocalDateTime.now();
    }
}

