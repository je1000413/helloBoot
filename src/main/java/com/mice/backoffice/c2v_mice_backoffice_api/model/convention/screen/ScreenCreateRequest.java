package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenStateCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ScreenCreateRequest {
    private long spaceObjectId;
    private DisplayCode displayCode;
    private ScreenStateCode stateCode;

    private List<ScreenDisplayCreateRequest> screenDisplays;


    @Builder
    public ScreenCreateRequest(long spaceObjectId, DisplayCode displayCode, ScreenStateCode stateCode, List<ScreenDisplayCreateRequest> screenDisplays) {
        this.spaceObjectId = spaceObjectId;
        this.displayCode = displayCode;
        this.stateCode = stateCode;
        this.screenDisplays = screenDisplays;
    }

    public List<ScreenDisplayCreateRequest> getScreenDisplays() {
        return Objects.isNull(screenDisplays) || screenDisplays.isEmpty() ? null : Collections.unmodifiableList(screenDisplays);
    }

    public static ScreenCreateRequest makeCreateRequest(ScreenUpdateRequest req) {
        List<ScreenDisplayCreateRequest> screenDisplayCreateRequests = req.getScreenDisplays().stream().map(ScreenDisplayCreateRequest::makeCreateRequest).toList();

        return builder()
                .spaceObjectId(req.getSpaceObjectId())
                .displayCode(req.getDisplayCode())
                .screenDisplays(screenDisplayCreateRequests)
                .stateCode(req.getStateCode())
                .build();
    }
}
