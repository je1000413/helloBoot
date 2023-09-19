package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionDisplayUpdateRequest {
    private long displaySeq;
    private String location;
    private DisplayCode displayCode;
    private DisplayType displayType;
    @NotNull(message = "displayContents is null")
    private Map<LanguageType, String> displayContents;
    private SessionDisplayStateCode stateCode;
    public Map<LanguageType, String> getDisplayContents() {
        return Objects.isNull(displayContents) || displayContents.isEmpty() ? null : Collections.unmodifiableMap(displayContents);
    }
}
