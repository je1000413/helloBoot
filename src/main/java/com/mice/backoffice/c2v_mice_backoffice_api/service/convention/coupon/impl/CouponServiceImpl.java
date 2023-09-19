package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.coupon.impl;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis.CouponMybatisRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    /* Repository */
    private final CouponMybatisRepository couponRespository;

    @Override
    public C2VResponse list(CouponListRequest req) {

        CouponListSqlRequest couponListSqlRequest = new CouponListSqlRequest();

        //일자별
        if(req.getDateType() != null && req.getStartDateTime() != null && req.getEndDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
            String startDatetime = req.getStartDateTime().format(formatter);
            String endDatetime = req.getEndDateTime().format(formatter);

            System.out.println("startDate " + startDatetime );
            System.out.println("endDate " + endDatetime );

            if (req.getDateType().equals(Supports.PackageSearchDateType.CREATE_DATE)) {
                couponListSqlRequest.setStartCreateDatetime(startDatetime);
                couponListSqlRequest.setEndCreateDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.PackageSearchDateType.EVENT_DATE)) {
                couponListSqlRequest.setStartEventDatetime(startDatetime);
                couponListSqlRequest.setEndEventDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.PackageSearchDateType.UPDATE_DATE)) {
                couponListSqlRequest.setStartUpdateDatetime(startDatetime);
                couponListSqlRequest.setEndUpdateDatetime(endDatetime);
            }
        }
        //행사 상태
        if(req.getEventStateCode() != null){
            couponListSqlRequest.setStateCode(String.valueOf(req.getEventStateCode().getType()));
        }
        //연사구분
        if(req.getStaffType() != null){
            couponListSqlRequest.setStaffType(String.valueOf(req.getStaffType().getType()));
        }
        //이름/코드
        if(req.getNameAndCodeType() != null && req.getNameAndCodeKeyword() != null){
            if(req.getNameAndCodeType().equals(Supports.PackageSearchNameAndCodeType.EVENT_NAME)){
                couponListSqlRequest.setEventName(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.PackageSearchNameAndCodeType.EVENT_CODE)){
                couponListSqlRequest.setEventId(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.PackageSearchNameAndCodeType.PACKAGE_NAME)){
                couponListSqlRequest.setPackageName(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.PackageSearchNameAndCodeType.PACKAGE_CODE)){
                couponListSqlRequest.setPackageId(req.getNameAndCodeKeyword());
            }
        }
        //도메인
        if(req.getDomainAndCodeType() != null && req.getDomainAndCodeKeyword() != null){
            if(req.getDomainAndCodeType().equals(Supports.PackageSearchDomainAndCodeType.HOST_NAME)){
                couponListSqlRequest.setCompanyName(req.getDomainAndCodeKeyword());
            }else if(req.getDomainAndCodeType().equals(Supports.PackageSearchDomainAndCodeType.HOST_CODE)){
                couponListSqlRequest.setCompanyId(req.getDomainAndCodeKeyword());
            }
        }

        if(req.getPageNum() != 0) couponListSqlRequest.setPageNum(req.getPageNum());
        if(req.getPageSize() != 0) couponListSqlRequest.setPageSize(req.getPageSize());


        List<Map<String,Object>> list = couponRespository.findCouponListByCondition(RequestUtils.getSqlRequest(couponListSqlRequest));
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Coupon List 조회 성공").data(list).build();
    }

    @Override
    public C2VResponse get(String packageId) {
        List<Map<String,Object>> map = couponRespository.findByPackageId(packageId);
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Package 조회 성공").data(map).build();
    }

    @Override
    public C2VResponse getCoupon(String packageId) {
        List<Map<String,Object>> map = couponRespository.findCouponListByPackageId(packageId);
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("발급 Coupon List 조회 성공").data(map).build();
    }

    @Override
    public C2VResponse createCoupon(CouponCreateRequest req) {

        CouponCreateSqlRequest couponCreateSqlRequest = new CouponCreateSqlRequest();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String couponNm = LocalDateTime.now().format(formatter1);

        int tryCnt = req.getCouponNum();
        while(tryCnt > 0){

            couponCreateSqlRequest.setPackageId(req.getPackageId());
            couponCreateSqlRequest.setCouponNum((tryCnt>=5000?(5000):tryCnt));
            couponCreateSqlRequest.setCouponLength(12);
            couponCreateSqlRequest.setCouponName(couponNm);
            couponCreateSqlRequest.setPrice(0);
            couponCreateSqlRequest.setStateCode(1);
            couponCreateSqlRequest.setUserId(RequestUtils.getCurAdminId());
            couponCreateSqlRequest.setStartDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            couponCreateSqlRequest.setEndDatetime(LocalDateTime.now().plusYears(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            couponCreateSqlRequest.setNowDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            couponRespository.makeCouponListByPackageId(RequestUtils.getSqlRequest(couponCreateSqlRequest));

            tryCnt -= 5000;
        }

        Map<String, Object> map = new HashMap<>();

        map.put("in_package_id", req.getPackageId());
        map.put("in_start_datetime", couponCreateSqlRequest.getStartDatetime());
        map.put("in_end_datetime", couponCreateSqlRequest.getEndDatetime());

        List<Map<String,Object>> list = couponRespository.findByPackageIdAndStartDatetimeAndEndDatetime(map);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Coupon 생성 성공").data(list).build();
    }

    @Override
    public C2VResponse logList(CouponLogListRequest req) {
        CouponLogListSqlRequest couponLogListSqlRequest = new CouponLogListSqlRequest();

        //일자별
        if(req.getDateType() != null && req.getStartDateTime() != null && req.getEndDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
            String startDatetime = req.getStartDateTime().format(formatter);
            String endDatetime = req.getEndDateTime().format(formatter);

            if (req.getDateType().equals(Supports.CouponLogDateType.CREATE_DATE)) {
                couponLogListSqlRequest.setStartCreateDatetime(startDatetime);
                couponLogListSqlRequest.setEndCreateDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.CouponLogDateType.EVENT_DATE)) {
                couponLogListSqlRequest.setStartEventDatetime(startDatetime);
                couponLogListSqlRequest.setEndEventDatetime(endDatetime);
            }
        }
        //연사구분
        if(req.getCouponLogType() != null){
            couponLogListSqlRequest.setCouponLogType(String.valueOf(req.getCouponLogType().getType()));
        }
        //이름/코드
        if(req.getNameAndCodeType() != null && req.getNameAndCodeKeyword() != null){
            if(req.getNameAndCodeType().equals(Supports.CouponLogSearchNameAndCodeType.EVENT_NAME)){
                couponLogListSqlRequest.setEventName(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.CouponLogSearchNameAndCodeType.EVENT_CODE)){
                couponLogListSqlRequest.setEventId(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.CouponLogSearchNameAndCodeType.PACKAGE_NAME)){
                couponLogListSqlRequest.setPackageName(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.CouponLogSearchNameAndCodeType.PACKAGE_CODE)){
                couponLogListSqlRequest.setPackageId(req.getNameAndCodeKeyword());
            }
        }
        //도메인
        if(req.getDomainAndCodeType() != null && req.getDomainAndCodeKeyword() != null){
            if(req.getDomainAndCodeType().equals(Supports.CouponLogSearchDomainAndCodeType.HOST_NAME)){
                couponLogListSqlRequest.setCompanyName(req.getDomainAndCodeKeyword());
            }else if(req.getDomainAndCodeType().equals(Supports.CouponLogSearchDomainAndCodeType.HOST_CODE)){
                couponLogListSqlRequest.setCompanyId(req.getDomainAndCodeKeyword());
            }
        }

        if(req.getPageNum() != 0) couponLogListSqlRequest.setPageNum(req.getPageNum());
        if(req.getPageSize() != 0) couponLogListSqlRequest.setPageSize(req.getPageSize());

        List<Map<String,Object>> list = couponRespository.findCouponLogListByCondition(RequestUtils.getSqlRequest(couponLogListSqlRequest));
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Coupon List 조회 성공").data(list).build();
    }


}
