package com.mice.backoffice.c2v_mice_backoffice_api.entity.system.group;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class GroupEntity {
    @Id
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "role_code")
    private Integer roleCode;

    @Column(name = "state_code")
    private Integer stateCode;
}
