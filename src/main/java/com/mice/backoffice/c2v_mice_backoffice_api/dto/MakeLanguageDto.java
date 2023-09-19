package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MakeLanguageDto {
    private Map<String, Map<LanguageType,String>> messageMap;
    private char useYn;
    private long createUserId;
    private long updateUserId;

    @Builder
    public MakeLanguageDto(Map<String, Map<LanguageType, String>> messageMap, char useYn, long createUserId, long updateUserId) {
        this.messageMap = messageMap;
        this.useYn = useYn;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }
}
