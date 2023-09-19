package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionSpaceInfoUpdateRequest {
    private DisplayCode displayCode;
    private List<ScreenUpdateRequest> sessionDisplays;

    public List<ScreenUpdateRequest> getSessionDisplays() {
        return Objects.isNull(sessionDisplays) || sessionDisplays.isEmpty() ? null : Collections.unmodifiableList(sessionDisplays);
    }
}
