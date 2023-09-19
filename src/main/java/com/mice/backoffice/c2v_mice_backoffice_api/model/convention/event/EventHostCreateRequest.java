package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.*;

@Getter
@NoArgsConstructor
public class EventHostCreateRequest {
    @NotBlank(message = "domainId is required")
    private int domainId;
    private HostCode hostCode;

    @Builder
    public EventHostCreateRequest(int domainId, HostCode hostCode) {
        this.domainId = domainId;
        this.hostCode = hostCode;
    }

    public static EventHostCreateRequest makeCreateRequest(EventHostUpdateRequest req) {
        return builder()
                .domainId(req.getDomainId())
                .hostCode(req.getHostCode())
                .build();
    }
}