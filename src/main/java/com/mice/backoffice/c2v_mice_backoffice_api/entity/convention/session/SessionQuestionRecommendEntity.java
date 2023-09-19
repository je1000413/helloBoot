package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account.AccountEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "session_question_recommend")
@Getter
@SuperBuilder
public class SessionQuestionRecommendEntity extends JpaBaseTimeEntity {
    @EmbeddedId
    private SessionQuestionRecommendId pk;
    private @Column(name = "use_yn") char useYn;

    @ManyToOne
    @JoinColumn(name = "question_seq", insertable = false, updatable = false)
    private SessionQuestionEntity question;

    @OneToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private AccountEntity account;

    @Getter
    @Embeddable
    @NoArgsConstructor
    public static class SessionQuestionRecommendId implements Serializable {
        private @Column(name = "question_seq") int questionSeq;
        private @Column(name = "account_id") long accountId;
    }
}
