package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.DomainEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "event_host")
@Getter
@SuperBuilder
public class EventHostEntity extends JpaBaseUserEntity {
    @EmbeddedId
    private EventHostId eventHostId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    @JsonIgnore
    private EventEntity event;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", insertable = false, updatable = false)
    @JsonIgnore
    private DomainEntity domain;

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EventHostId implements Serializable {
        private @Column(name = "event_id") long eventId;
        private @Column(name = "domain_id") int domainId;
        private @Column(name = "host_code") HostCode hostCode;
        @Builder
        public EventHostId(long eventId, int  domainId, HostCode hostCode) {
            this.eventId = eventId;
            this.domainId = domainId;
            this.hostCode = hostCode;
        }
    }


    public void update(EventHostUpdateRequest req) {
        this.eventHostId.hostCode = req.getHostCode();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static EventHostEntityBuilder dataBuilder(EventHostId eventHostId) {
        return builder()
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId())
                .eventHostId(eventHostId);
    }
}
