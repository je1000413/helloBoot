package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CouponListRequest {

    private Supports.PackageSearchDateType dateType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private EventSupports.EventStateCode eventStateCode;
    private Supports.CouponLogType staffType;
    private Supports.PackageSearchNameAndCodeType nameAndCodeType;
    private String nameAndCodeKeyword;
    private Supports.PackageSearchDomainAndCodeType domainAndCodeType;
    private String domainAndCodeKeyword;
    private int pageNum;
    private int pageSize;
}
