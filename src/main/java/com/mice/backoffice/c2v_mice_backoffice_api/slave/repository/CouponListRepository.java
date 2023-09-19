package com.mice.backoffice.c2v_mice_backoffice_api.slave.repository;

import com.mice.backoffice.c2v_mice_backoffice_api.slave.entity.CouponListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponListRepository extends Repository<CouponListEntity, String> {

    @Procedure(procedureName = "sp_platform_coupon_select_list")
    //@Query(value = "{ CALL sp_platform_coupon_select_list(:in_start_create_datetime, :in_end_create_datetime, :in_start_event_datetime, :in_end_event_datetime, :in_start_update_datetime, :in_end_update_datetime, :in_state_code, :in_staff_type, :in_package_name, :in_package_id, :in_event_name, :in_event_id, :in_company_name, :in_company_id, :in_page_size, :in_page_num) }", nativeQuery = true)
    List<CouponListEntity> findCouponListEntitiesByCondition(
            @Param("in_start_create_datetime") String in_start_create_datetime,
            @Param("in_end_create_datetime") String in_end_create_datetime,
            @Param("in_start_event_datetime") String in_start_event_datetime,
            @Param("in_end_event_datetime") String in_end_event_datetime,
            @Param("in_start_update_datetime") String in_start_update_datetime,
            @Param("in_end_update_datetime") String in_end_update_datetime,
            @Param("in_state_code") String in_state_code,
            @Param("in_staff_type") String in_staff_type,
            @Param("in_package_name") String in_package_name,
            @Param("in_package_id") String in_package_id,
            @Param("in_event_name") String in_event_name,
            @Param("in_event_id") String in_event_id,
            @Param("in_company_name") String in_company_name,
            @Param("in_company_id") String in_company_id,
            @Param("in_page_size") int in_page_size,
            @Param("in_page_num") int in_page_num
    );
}
