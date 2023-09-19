package com.mice.backoffice.c2v_mice_backoffice_api.entity.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminInfoResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin")
@Getter
public class AdminEntity extends JpaBaseUserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    @Column(name = "account_password")
    private String accountPassword;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "password_try_count")
    private int passwordTryCount;
    @Column(name = "domain_id")
    private Integer domainId;
    @Column(name = "name")
    private String name;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "mail_address")
    private String mailAddress;
    @Column(name = "tel_no")
    private String telNo;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "state_code")
    private Integer stateCode;
    @Column(name = "accept_datetime")
    private LocalDateTime acceptDatetime;
    @Column(name = "last_login_datetime")
    private LocalDateTime lastLoginDatetime;

    public void update(String accountPassword) {
        this.accountPassword = accountPassword;
        this.acceptDatetime = LocalDateTime.now();
    }

    public void updateForLastLoginDatetime() {
        this.passwordTryCount = 0;
        this.lastLoginDatetime = LocalDateTime.now();
    }

    public void updateForPasswordFail() {
        this.passwordTryCount++;
    }


    public AdminInfoResponse getCreateUserInfo(LocalDateTime createDatetime) {
        return AdminInfoResponse
                .builder()
                .userId(this.adminId)
                .name(this.name)
                .accountName(this.accountName)
                .dateTime(createDatetime)
                .build();
    }

    public AdminInfoResponse getUpdateUserInfo(LocalDateTime updateDatetime) {
        return AdminInfoResponse
                .builder()
                .userId(this.adminId)
                .name(this.name)
                .accountName(this.accountName)
                .dateTime(updateDatetime)
                .build();
    }

/*    public String getCreateUserName(LocalDateTime createDateTime) {
        return String.format("%s(%s) / %s",
                name,
                accountName,
                createDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
    }

    public String getUpdateUserName(LocalDateTime updateDateTime) {
        return String.format("%s(%s) / %s",
                name,
                accountName,
                updateDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
    }*/
}

