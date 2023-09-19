package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.HostCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventHostUpdateRequest {
    @NotBlank(message = "domainId is required")
    private int domainId;
    private HostCode hostCode;

    @Builder
    public EventHostUpdateRequest(int domainId, HostCode hostCode) {
        this.domainId = domainId;
        this.hostCode = hostCode;
    }
}
