package com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CouponMybatisRepository {
    List<Map<String,Object>> findCouponListByCondition(Map<String,Object> request);
    List<Map<String,Object>> makeCouponListByPackageId(Map<String,Object> request);
    List<Map<String, Object>> findCouponLogListByCondition(Map<String, Object> sqlRequest);
    List<Map<String, Object>> findByPackageId(String packageId);
    List<Map<String, Object>> findCouponListByPackageId(String packageId);
    List<Map<String, Object>> findByPackageIdAndStartDatetimeAndEndDatetime(Map<String,Object> request);
}
