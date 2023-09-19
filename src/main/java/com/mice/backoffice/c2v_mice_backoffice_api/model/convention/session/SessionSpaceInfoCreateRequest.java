package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionSpaceInfoCreateRequest {
    private DisplayCode displayCode;
    private List<ScreenCreateRequest> sessionDisplays;

    @Builder
    public SessionSpaceInfoCreateRequest(DisplayCode displayCode, List<ScreenCreateRequest> sessionDisplays) {
        this.displayCode = displayCode;
        this.sessionDisplays = sessionDisplays;
    }

    public List<ScreenCreateRequest> getSessionDisplays() {
        return Objects.isNull(sessionDisplays) || sessionDisplays.isEmpty() ? null : Collections.unmodifiableList(sessionDisplays);
    }

    public static SessionSpaceInfoCreateRequest makeCreateRequest(SessionSpaceInfoUpdateRequest req) {
        List<ScreenCreateRequest> sessionDisplayCreateRequests = req.getSessionDisplays().stream().map(ScreenCreateRequest::makeCreateRequest).toList();

        return builder()
                .displayCode(req.getDisplayCode())
                .sessionDisplays(sessionDisplayCreateRequests)
                .build();
    }
}
