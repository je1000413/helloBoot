package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionStaffEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Table(name = "language")
@Getter
public class LanguageEntity extends JpaBaseUserEntity {
    @EmbeddedId
    private LanguageId languageId;
    private @Column(name = "message") String message;
    private @Column(name = "use_yn") char useYn;

    public void updateLanguageEntity(String message) {
        this.message = message;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.useYn = 'n';
        updateUserId(RequestUtils.getCurAdminId());
    }

    public LanguageType getLanguageType() {
        return this.languageId.getLanguageType();
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LanguageId implements Serializable {
        @Column(name = "language_code")
        private String languageCode;
        @Column(name = "language_type")
        private LanguageType languageType;

        @Builder
        public LanguageId(String languageCode, LanguageType languageType) {
            this.languageCode = languageCode;
            this.languageType = languageType;
        }
    }
}
