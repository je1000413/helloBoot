package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LoungeStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Table(name = "lounge")
@Getter
public class LoungeEntity extends JpaBaseUserEntity {
    @EmbeddedId
    private LoungeId pk;
    private @Column(name = "lounge_name") String loungeName;
    private @Column(name = "space_id") String spaceId;
    private @Column(name = "template_id") long templateId;
    private @Column(name = "state_code") LoungeStateCode stateCode;
    private @Column(name = "lounge_type") Supports.LoungeType loungeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;

    public void updateLounge(long templateId) {
        this.templateId = templateId;
        updateUserId(RequestUtils.getCurAdminId());
        forceUpdate();
    }

    public void deleteLounge(LoungeStateCode delete) {
        this.stateCode = delete;
        updateUserId(RequestUtils.getCurAdminId());
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoungeId implements Serializable {
        private @Column(name = "event_id") long eventId;
        private @Column(name = "lounge_no") int loungeNo;

        @Builder
        public LoungeId(long eventId, int loungeNo) {
            this.eventId = eventId;
            this.loungeNo = loungeNo;
        }
    }
}
