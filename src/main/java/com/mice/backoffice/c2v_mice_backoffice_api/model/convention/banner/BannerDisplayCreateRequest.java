package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class BannerDisplayCreateRequest {
    @NotNull(message = "bannerId is null")
    private long bannerId;
    @NotNull(message = "displayContents is null")
    private Map<Supports.LanguageType,String> displayContents;
    private Map<Supports.LanguageType,String> linkAddress;
    private LocalDateTime displayStartDatetime;
    private  LocalDateTime displayEndDatetime;
    @NotNull(message = "alwaysDisplayYn is null")
    private char alwaysDisplayYn;
    @NotNull(message = "stateCode is null")
    private Supports.BannerDisplayStateCode stateCode;

}
