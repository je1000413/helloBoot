package com.mice.backoffice.c2v_mice_backoffice_api.dto.event;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventLanguageDto {
    private String nameKr;
    private String nameUs;
    private String descriptionKr;
    private String descriptionUs;
    private String locationKr;
    private String locationUs;
    private LanguageType nameKrCode;
    private LanguageType nameUsCode;
    private LanguageType descriptionKrCode;
    private LanguageType descriptionUsCode;
    private LanguageType locationKrCode;
    private LanguageType locationUsCode;
}
