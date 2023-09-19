package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionDisplayCreateRequest {
    private String location;
    private DisplayCode displayCode;
    private DisplayType displayType;
    private SessionDisplayStateCode stateCode;
    @NotNull(message = "displayContents is null")
    private Map<LanguageType, String> displayContents;

    @Builder
    public SessionDisplayCreateRequest(String location, DisplayCode displayCode, DisplayType displayType, SessionDisplayStateCode stateCode, Map<LanguageType, String> displayContents) {
        this.location = location;
        this.displayCode = displayCode;
        this.displayType = displayType;
        this.displayContents = displayContents;
        this.stateCode = stateCode;
    }

    public Map<LanguageType, String> getDisplayContents() {
        return Objects.isNull(displayContents) || displayContents.isEmpty() ? new EnumMap<>(LanguageType.class) : Collections.unmodifiableMap(displayContents);
    }

    public static SessionDisplayCreateRequest makeCreateRequest(SessionDisplayUpdateRequest req) {
        return builder()
                .location(req.getLocation())
                .stateCode(req.getStateCode())
                .displayCode(req.getDisplayCode())
                .displayType(req.getDisplayType())
                .displayContents(req.getDisplayContents())
                .build();
    }
}
