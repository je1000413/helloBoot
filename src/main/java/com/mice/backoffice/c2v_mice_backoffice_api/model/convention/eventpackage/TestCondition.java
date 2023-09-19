package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageSearchDateType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TestCondition {
    private PackageSearchDateType dateType;

    // 판매상태
    private int stateOption;

    // 연사 구분
    private int staffOption;

    // 이름/코드
    private int nameAndCodeType;
    private String nameAndCodeKeyword;

    // 고객사/코드
    private int domainAndCodeType;
    private String domainAndCodeKeyword;

    // 페이지 넘버
    private int pageNum;

    // 페이지당 목록
    private int pageSize;
}
