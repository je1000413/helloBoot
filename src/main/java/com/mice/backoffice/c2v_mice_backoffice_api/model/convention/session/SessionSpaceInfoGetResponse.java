package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SessionSpaceInfoGetResponse {
    private DisplayCode displayCode;
    private List<ScreenGetResponse> sessionDisplays;

    @Builder
    public SessionSpaceInfoGetResponse(DisplayCode displayCode, List<ScreenGetResponse> sessionDisplays) {
        this.displayCode = displayCode;
        this.sessionDisplays = sessionDisplays;
    }

    public static SessionSpaceInfoGetResponseBuilder dataBuilder(DisplayCode displayCode, List<ScreenGetResponse> sessionDisplays) {
        return builder()
                .displayCode(displayCode)
                .sessionDisplays(sessionDisplays);
    }
}
