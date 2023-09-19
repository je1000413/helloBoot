package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenStateCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ScreenUpdateRequest {
    private long screenId;
    private long spaceObjectId;
    private DisplayCode displayCode;
    private ScreenStateCode stateCode;

    private List<ScreenDisplayUpdateRequest> screenDisplays;

    @Builder
    public ScreenUpdateRequest(long screenId, long spaceObjectId, DisplayCode displayCode, ScreenStateCode stateCode, List<ScreenDisplayUpdateRequest> screenDisplays) {
        this.screenId = screenId;
        this.spaceObjectId = spaceObjectId;
        this.displayCode = displayCode;
        this.screenDisplays = screenDisplays;
        this.stateCode = stateCode;
    }

    public List<ScreenDisplayUpdateRequest> getScreenDisplays() {
        return Objects.isNull(screenDisplays) || screenDisplays.isEmpty() ? null : Collections.unmodifiableList(screenDisplays);
    }
}
