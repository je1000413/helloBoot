package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CouponLogListRequest {

    private Supports.CouponLogDateType dateType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Supports.CouponLogType couponLogType;
    private Supports.CouponLogSearchNameAndCodeType nameAndCodeType;
    private String nameAndCodeKeyword;
    private Supports.CouponLogSearchDomainAndCodeType domainAndCodeType;
    private String domainAndCodeKeyword;
    private int pageNum;
    private int pageSize;
}
