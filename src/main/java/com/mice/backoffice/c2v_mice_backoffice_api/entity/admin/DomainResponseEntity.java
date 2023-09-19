package com.mice.backoffice.c2v_mice_backoffice_api.entity.admin;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "domain")
public class DomainResponseEntity {
    @Id
    @Column(name = "domain_id")
    private Integer domainId;

    @Column(name = "domain_name")
    private String domainName;
}
