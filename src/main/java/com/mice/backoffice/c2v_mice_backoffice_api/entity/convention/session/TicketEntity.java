package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ticket")
@Getter
@SuperBuilder
public class TicketEntity extends JpaBaseUserEntity {
    @EmbeddedId
    private TicketId ticketId;
    private @Column(name = "ticket_name") String ticketName;
    private @Column(name = "online_code") TicketOnlineCode ticketOnlineCode;
    private @Column(name = "state_code") TicketStateCode ticketStateCode;

    public void update(TicketUpdateRequest req) {
        this.ticketOnlineCode = req.getTicketOnlineCode();
    }

    @Getter
    @Embeddable
    @NoArgsConstructor
    public static class TicketId implements Serializable {
        private @Column(name = "ticket_id") String ticketId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "session_id")
        private SessionEntity session;

        @Builder
        public TicketId(String ticketId, SessionEntity session, PackageEntity packageEntity) {
            this.ticketId = ticketId;
            this.session = session;
        }
    }

    public static TicketEntityBuilder dataBuilder(TicketCreateRequest req) {
        return builder()
                .ticketOnlineCode(req.getTicketOnlineCode())
                .ticketStateCode(TicketStateCode.NORMAL)
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }
}

