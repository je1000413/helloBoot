package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.HostCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostGetResponse.EventHostDetailGetResponse.EventHostDetailGetResponseBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class EventHostGetResponse {
    private String eventHostNames;
    private List<EventHostDetailGetResponse> eventHosts;

    @Builder
    public EventHostGetResponse(String eventHostNames, List<EventHostDetailGetResponse> eventHosts) {
        this.eventHostNames = eventHostNames;
        this.eventHosts = eventHosts;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class EventHostDetailGetResponse {
        private HostCode hostCode;
        private int domainId;
        private Map<Supports.LanguageType, String> eventHostName;
        private long createUserId;
        private long updateUserId;

        @Builder
        public EventHostDetailGetResponse(HostCode hostCode, int domainId, long createUserId, long updateUserId, Map<Supports.LanguageType, String> eventHostName) {
            this.hostCode = hostCode;
            this.domainId = domainId;
            this.createUserId = createUserId;
            this.updateUserId = updateUserId;
            this.eventHostName = eventHostName;
        }

        public static EventHostDetailGetResponseBuilder dataBuilder(EventHostEntity ehr) {
            return builder()
                    .hostCode(ehr.getEventHostId().getHostCode())
                    .domainId(ehr.getDomain().getDomainId())
                    .createUserId(ehr.getCreateUserId())
                    .updateUserId(ehr.getUpdateUserId());
        }
    }
}
