package com.mice.backoffice.c2v_mice_backoffice_api.entity.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.Menu.MenuEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AdminResponseEntity {

    @Id
    @Column(name="admin_id")
    private Long admin_id;

    @Column(name="account_name")
    private String accountName;

    @Column(name="name")
    private String name;

    @Column(name="domain_id")
    private String domainId;

    @Column(name="domain_name")
    private String domainName;

    @Column(name="nickname")
    private String nickname;

    @Column(name="mail_address")
    private String mailAddress;

    @Column(name="tel_no")
    private String telNo;

    @Column(name="state_code")
    private Integer stateCode;

    @Column(name="state_name")
    private String stateName;

    @Column(name="accept_datetime")
    private LocalDateTime acceptDatetime;

    @Column(name="last_login_datetime")
    private LocalDateTime lastLoginDatetime;

    @Column(name="create_datetime")
    private LocalDateTime createDatetime;

    @Column(name="update_datetime")
    private LocalDateTime updateDatetime;

    @Column(name="group_id")
    private Integer groupId;

    @Column(name="group_name")
    private String groupName;

    @Column(name="role_code")
    private Integer roleCode;

    @Column(name="role_name")
    private String roleName;

    @Transient
    private List<AdminMenuResponseEntity> adminMenus;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    public static class AdminMenuResponseEntity extends MenuEntity {
        @Column(name = "depth")
        private Integer depth;
        @Column(name = "menu_sort_name")
        private String menuSortName;
        @Column(name = "group_id")
        private Integer groupId;
        @Column(name = "group_name")
        private String groupName;
        @Column(name="authority_code")
        private Integer authorityCode;
        @Column(name="authority_name")
        private String authorityName;
    }
}
