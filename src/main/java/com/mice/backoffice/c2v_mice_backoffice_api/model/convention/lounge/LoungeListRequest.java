package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoungeListRequest {

    private Supports.LoungeSearchDateType dateType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long templateId;
    private Supports.LoungeSearchNameAndCodeType nameAndCodeType;
    private String nameAndCodeKeyword;
    private Supports.LoungeSearchDomainAndCodeType domainAndCodeType;
    private String domainAndCodeKeyword;
    private int pageNum;
    private int pageSize;

}
