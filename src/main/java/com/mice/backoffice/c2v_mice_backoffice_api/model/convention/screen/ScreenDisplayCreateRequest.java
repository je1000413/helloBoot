package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenDisplayStateCode;
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
public class ScreenDisplayCreateRequest {
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
    public ScreenDisplayCreateRequest(int pageNo, DisplayType displayType,
                                      char soundYn, ScreenDisplayStateCode stateCode,
                                      Map<LanguageType, String> linkAddress, Map<LanguageType, String> displayContents,
                                      Map<LanguageType, String> fileName) {
        this.pageNo = pageNo;
        this.displayType = displayType;
        this.soundYn = soundYn;
        this.stateCode = stateCode;
        this.linkAddress = linkAddress;
        this.displayContents = displayContents;
        this.fileName = fileName;
    }

    @Builder
    public ScreenDisplayCreateRequest(ScreenDisplayUpdateRequest sdur) {
        this.pageNo = sdur.getPageNo();
        this.displayType = sdur.getDisplayType();
        this.soundYn = sdur.getSoundYn();
        this.stateCode = sdur.getStateCode();
        this.linkAddress = sdur.getLinkAddress();
        this.displayContents = sdur.getDisplayContents();
        this.fileName = sdur.getFileName();
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

    public static ScreenDisplayCreateRequest makeCreateRequest(ScreenDisplayUpdateRequest req) {
        Map<LanguageType, String> displayContentsMap = Objects.isNull(req.getDisplayContents()) || req.getDisplayContents().isEmpty() ? new EnumMap<>(LanguageType.class) : req.getDisplayContents();
        Map<LanguageType, String> linkAddressMap = Objects.isNull(req.getLinkAddress()) || req.getLinkAddress().isEmpty() ? new EnumMap<>(LanguageType.class) : req.getLinkAddress();
        Map<LanguageType, String> fileNameMap = Objects.isNull(req.getFileName()) || req.getFileName().isEmpty() ? new EnumMap<>(LanguageType.class) : req.getFileName();

        if (displayContentsMap.isEmpty())
            displayContentsMap.put(LanguageType.KO_KR, "");

        if (linkAddressMap.isEmpty())
            linkAddressMap.put(LanguageType.KO_KR, "");

        if (fileNameMap.isEmpty())
            fileNameMap.put(LanguageType.KO_KR, "");


        return builder()
                .pageNo(req.getPageNo())
                .displayType(req.getDisplayType())
                .linkAddress(linkAddressMap)
                .displayContents(displayContentsMap)
                .fileName(fileNameMap)
                .build();
    }
}
