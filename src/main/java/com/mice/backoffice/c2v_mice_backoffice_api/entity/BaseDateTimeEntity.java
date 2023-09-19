package com.mice.backoffice.c2v_mice_backoffice_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaseDateTimeEntity
{
    @Column(name = "create_dt")
    @CreatedDate
    private LocalDateTime createDt;
    @Column(name = "update_dt")
    @LastModifiedDate
    private LocalDateTime updateDt;
}
