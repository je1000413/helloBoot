package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "session_display")
@Getter
@SuperBuilder
public class SessionDisplayEntity extends JpaBaseUserEntity {
    @EmbeddedId
    private SessionDisplayId sessionDisplayId;
    private @Column(name = "location") String location;
    private @Column(name = "display_code") DisplayCode displayCode;
    private @Column(name = "display_type") DisplayType displayType;
    private @Column(name = "display_contents") String displayContents;
    private @Column(name = "state_code") SessionDisplayStateCode stateCode;

    public void update(SessionDisplayUpdateRequest req) {
        this.location = req.getLocation();
        this.displayCode = req.getDisplayCode();
        this.displayType = req.getDisplayType();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.stateCode = SessionDisplayStateCode.DELETE;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static SessionDisplayEntityBuilder dataBuilder(SessionDisplayCreateRequest req) {
        return builder()
                .location(req.getLocation())
                .displayCode(req.getDisplayCode())
                .displayType(req.getDisplayType())
                .stateCode(req.getStateCode())
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SessionDisplayId implements Serializable {
        @Column(name = "display_seq")
        private long displaySeq;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "session_id")
        private SessionEntity session;

        @Builder
        public SessionDisplayId(long displaySeq, SessionEntity session) {
            this.displaySeq = displaySeq;
            this.session = session;
        }
    }

}
