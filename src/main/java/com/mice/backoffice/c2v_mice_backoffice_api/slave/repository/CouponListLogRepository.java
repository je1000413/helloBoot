package com.mice.backoffice.c2v_mice_backoffice_api.slave.repository;

import com.mice.backoffice.c2v_mice_backoffice_api.slave.entity.CouponListLogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CouponListLogRepository extends Repository<CouponListLogEntity, Integer> {

    //@Query(value = "{ CALL sp_platform_coupon_select_log_list(:in_start_create_datetime, :in_end_create_datetime, :in_start_event_datetime, :in_end_event_datetime, :in_coupon_log_type, :in_package_name, :in_package_id, :in_event_name, :in_event_id, :in_company_name, :in_company_id, :in_page_size, :in_page_num) }", nativeQuery = true)
    @Procedure(procedureName = "sp_platform_coupon_select_log_list")
    List<CouponListLogEntity> findCouponListLogEntitiesByCondition(
            String in_start_create_datetime,
            String in_end_create_datetime,
            String in_start_event_datetime,
            String in_end_event_datetime,
            String in_coupon_log_type,
            String in_package_name,
            String in_package_id,
            String in_event_name,
            String in_event_id,
            String in_company_name,
            String in_company_id,
            int in_page_size,
            int in_page_num
    );
}
