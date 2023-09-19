package com.mice.backoffice.c2v_mice_backoffice_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BaseEntity {

    @Id
    @Column(name = "admin_id")
    private Long adminId;
}
