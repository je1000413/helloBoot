package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CouponCreateSqlRequest {
    @Column(name = "in_package_id")
    private String packageId;
    @Column(name = "in_coupon_num")
    private int couponNum;
    @Column(name = "in_coupon_length")
    private int couponLength;
    @Column(name = "in_coupon_name")
    private String couponName;
    @Column(name = "in_price")
    private long price;
    @Column(name = "in_state_code")
    private int stateCode;
    @Column(name = "in_user_id")
    private long userId;
    @Column(name = "in_start_datetime")
    private String startDatetime;
    @Column(name = "in_end_datetime")
    private String endDatetime;
    @Column(name = "in_now_datetime")
    private String nowDatetime;
}
