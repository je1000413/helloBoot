package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponCreateRequest {
    @NotNull(message = "packageId is null")
    private String packageId;
    @NotNull(message = "couponNum is null")
    @Max(value = 5000, message = "couponNum has to be less than or equal to 5000")
    private int couponNum;
}
