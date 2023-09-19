package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account")
@Getter
public class AccountDetailEntity extends BaseDateTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "detail_seq") int detailSeq;
    private @Column(name = "detail_name") String detailName;
    private @Column(name = "detail_value") String detailValue;
    private @Column(name = "use_yn") char useYn = 'y';

    @OneToOne(mappedBy = "accountDetail")
    private AccountEntity account;

}
