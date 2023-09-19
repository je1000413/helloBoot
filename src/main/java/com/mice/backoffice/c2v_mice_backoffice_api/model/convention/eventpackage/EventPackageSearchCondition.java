package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventPackageSearchCondition {
    // 일자별
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private PackageSearchDateType dateType;

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
