package com.mice.backoffice.c2v_mice_backoffice_api.slave.service;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponLogListRequest;

public interface CouponListService {

    C2VResponse listBySlave(CouponListRequest request);

    C2VResponse loglistBySlave(CouponLogListRequest request);

}
