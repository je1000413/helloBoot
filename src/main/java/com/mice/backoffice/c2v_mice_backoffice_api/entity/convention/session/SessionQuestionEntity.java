package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account.AccountEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "session_question")
@Getter
@SuperBuilder
public class SessionQuestionEntity extends JpaBaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "question_seq") int questionSeq;
    private @Column(name = "session_id") long sessionId;
    private @Column(name = "account_id") long accountId;
    private @Column(name = "question_title") String questionTitle;
    private @Column(name = "question_description") String questionDescription;
    private @Column(name = "state_code") SessionQuestionStateCode stateCode;
    private @Column(name = "view_count") int viewCount;
    private @Column(name = "use_yn") char useYn;
    private @Column(name = "update_user_id") long updateUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private SessionEntity session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;

    @OneToMany(mappedBy = "question")
    private List<SessionQuestionRecommendEntity> sessionQuestionRecommends = new ArrayList<>();

    public void changeState(SessionQuestionStateCode stateCode) {
        this.stateCode = stateCode;
    }
}
