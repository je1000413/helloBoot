package com.mice.backoffice.c2v_mice_backoffice_api.slave.repository;

import com.mice.backoffice.c2v_mice_backoffice_api.slave.entity.EventListEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventListRepository extends Repository<EventListEntity, Long> {
    //@Query(value = "{ CALL sp_platform_event_select_for_paging(:in_cur_admin_id, :in_search_date_type, :in_search_date_s_datetime, :in_search_date_e_datetime, :in_search_status_type, :in_search_event_type, :in_search_event_value, :in_search_domain_type, :in_search_domain_value, :in_page_size, :in_page_num, :in_lounge_yn) }", nativeQuery = true)
    @Procedure(procedureName = "sp_platform_event_select_for_paging")
    List<EventListEntity> findEventForList(
            @Param("in_cur_admin_id") Long in_cur_admin_id,
            @Param("in_search_date_type") String in_search_date_type,
            @Param("in_search_date_s_datetime") String in_search_date_s_datetime,
            @Param("in_search_date_e_datetime") String in_search_date_e_datetime,
            @Param("in_search_status_type") int in_search_status_type,
            @Param("in_search_event_type") String in_search_event_type,
            @Param("in_search_event_value") String in_search_event_value,
            @Param("in_search_domain_type") String in_search_domain_type,
            @Param("in_search_domain_value") String in_search_domain_value,
            @Param("in_page_size") int in_page_size,
            @Param("in_page_num") int in_page_num,
            @Param("in_lounge_yn") String in_lounge_yn
    );
}
