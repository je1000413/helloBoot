package com.mice.backoffice.c2v_mice_backoffice_api.dto.lounge;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LoungeStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LoungeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoungeNameDto {
    private Map<LanguageType, String> name;
    private LoungeType type;
    private LoungeStateCode stateCode;

    @Builder
    public LoungeNameDto(Map<LanguageType, String> name, LoungeType type, LoungeStateCode stateCode) {
        this.name = name;
        this.type = type;
        this.stateCode = stateCode;
    }
}
