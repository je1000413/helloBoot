package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenDisplayStateCode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ScreenDisplayUpdateRequest {
    private int displaySeq;
    private int pageNo;
    private DisplayType displayType;
    private char soundYn;
    private ScreenDisplayStateCode stateCode;
    @NotNull(message = "linkAddress is null")
    private Map<LanguageType, String> linkAddress;
    @NotNull(message = "displayContents is null")
    private Map<LanguageType, String> displayContents;
    private Map<LanguageType, String> fileName;

    @Builder
    public ScreenDisplayUpdateRequest(int displaySeq, int pageNo, DisplayType displayType, char soundYn, Map<LanguageType, String> displayContents, ScreenDisplayStateCode stateCode, Map<LanguageType, String> linkAddress, Map<LanguageType, String> fileName) {
        this.displaySeq = displaySeq;
        this.pageNo = pageNo;
        this.displayType = displayType;
        this.soundYn = soundYn;
        this.displayContents = displayContents;
        this.linkAddress = linkAddress;
        this.fileName = fileName;
        this.stateCode = stateCode;
    }

    public Map<LanguageType, String> getDisplayContents() {
        return Objects.isNull(displayContents) || displayContents.isEmpty() ? null : Collections.unmodifiableMap(displayContents);
    }

    public Map<LanguageType, String> getLinkAddress() {
        return Objects.isNull(linkAddress) || linkAddress.isEmpty() ? null : Collections.unmodifiableMap(linkAddress);
    }

    public Map<LanguageType, String> getFileName() {
        return Objects.isNull(fileName) || fileName.isEmpty() ? null : Collections.unmodifiableMap(fileName);
    }
}
