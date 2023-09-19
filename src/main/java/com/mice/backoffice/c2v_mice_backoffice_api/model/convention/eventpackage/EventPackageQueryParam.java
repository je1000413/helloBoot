package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventPackageQueryParam {
    // 일자별
    private PackageSearchDateType dateType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // 판매상태
    private PackageStateCode stateOption;

    // 연사 구분
    private PackageSearchStaffType staffOption;

    // 이름/코드
    private PackageSearchNameAndCodeType nameAndCodeType;
    private String nameAndCodeKeyword;

    // 고객사/코드
    private PackageSearchDomainAndCodeType domainAndCodeType;
    private String domainAndCodeKeyword;

    // 페이지 넘버
    private int pageNum;

    // 페이지당 목록
    private int pageSize;

}
