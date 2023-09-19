package com.mice.backoffice.c2v_mice_backoffice_api.entity.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AdminListEntity {

    @Column(name="total_count")
    @JsonIgnore
    private Integer totalCount;

    @Column(name="row_num")
    private Integer rowNum;

    @Id
    @Column(name="admin_id")
    private Long admin_id;

    @Column(name="account_name")
    private String accountName;

    @Column(name="name")
    private String name;
    @Column(name="domain_id")
    private Integer domainId;
    @Column(name="domain_name")
    private String domainName;

    @Column(name="mail_address")
    private String mailAddress;

    @Column(name="tel_no")
    private String telNo;

    @Column(name="state_code")
    private Integer stateCode;

    @Column(name="state_name")
    private String stateName;

    @Column(name="group_id")
    private Integer groupId;

    @Column(name="group_name")
    private String groupName;

    @Column(name="role_code")
    private Integer roleCode;

    @Column(name="role_name")
    private String roleName;

    @Column(name="create_datetime")
    private String createDatetime;

    @Column(name="last_login_datetime")
    private String lastLoginDatetime;



}
