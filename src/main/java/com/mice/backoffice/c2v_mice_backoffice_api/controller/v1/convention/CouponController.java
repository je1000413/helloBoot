package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.CouponLogListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.coupon.CouponService;
import com.mice.backoffice.c2v_mice_backoffice_api.slave.service.CouponListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {
    // Service
    private final CouponService couponService;

    private final CouponListService couponListService;

    @PostMapping("/list")
    public C2VResponse list(@RequestBody CouponListRequest request) throws C2VException {
        //return couponService.list(request);
        return couponListService.listBySlave(request);
    }
    @GetMapping("/package/{packageId}")
    public C2VResponse get(@PathVariable String packageId) throws C2VException {
        return couponService.get(packageId);
    }
    @PostMapping
    public C2VResponse createCoupon(@Valid @RequestBody CouponCreateRequest request) throws C2VException {
        return couponService.createCoupon(request);
    }
    @GetMapping("/{packageId}")
    public C2VResponse getCoupon(@PathVariable String packageId) throws C2VException {
        return couponService.getCoupon(packageId);
    }
    @PostMapping("/log/list")
    public C2VResponse logList(@RequestBody CouponLogListRequest request) throws C2VException {
        //return couponService.logList(request);
        return couponListService.loglistBySlave(request);
    }
}
