package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.coupon;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponLogListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.*;
import org.springframework.transaction.annotation.Transactional;


public interface CouponService {
    C2VResponse list(CouponListRequest request);

    C2VResponse createCoupon(CouponCreateRequest request);

    C2VResponse logList(CouponLogListRequest request);

    C2VResponse get(String packageId);

    C2VResponse getCoupon(String packageId);


}
