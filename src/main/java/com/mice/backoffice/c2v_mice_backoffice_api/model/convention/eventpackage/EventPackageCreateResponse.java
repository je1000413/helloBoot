package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventPackageCreateResponse {
    private String packageId;

    @Builder
    public EventPackageCreateResponse(String packageId) {
        this.packageId = packageId;
    }
}
