package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AccountSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.AccountSupports.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account")
@Getter
public class AccountEntity extends BaseDateTimeEntity {
    @Id
    private @Column(name = "account_id") long accountId;
    private @Column(name = "surname") String surName;
    private @Column(name = "given_name") String givenName;
    private @Column(name = "middle_name") String middleName;
    private @Column(name = "domain_id") int domainId;
    private @Column(name = "photo_path") String photoPath;
    private @Column(name = "tel_no") String telNo;
    private @Column(name = "company_name") String companyName;
    private @Column(name = "mail_address") String mailAddress;
    private @Column(name = "class_code") ClassCode classCode;
    private @Column(name = "public_yn") char publicYn = 'y';

    @OneToOne
    @JoinColumn(name = "detail_seq")
    private AccountDetailEntity accountDetail;
}
