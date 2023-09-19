package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogActionCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogMappingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLogCreateDto {
    private String mappingId;
    private AdminLogMappingType mappingType;
    private AdminLogActionCode actionCode;
    private String message;

    @Builder
    public AdminLogCreateDto(String mappingId, AdminLogMappingType mappingType, AdminLogActionCode actionCode, String message) {
        this.mappingId = mappingId;
        this.mappingType = mappingType;
        this.actionCode = actionCode;
        this.message = message;
    }
}
