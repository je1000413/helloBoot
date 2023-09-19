package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value = "SELECT nextval('event')", nativeQuery = true)
    long findEventId();

    @Query( "select e.eventId " +
            "from EventEntity e " +
            "where e.stateCode in :eventStateCodeList " +
            "and e.eventSpaceStateCode = :eventSpaceStateCode")
    List<String> findByStateCodeAndSpaceStateCode(@Param("eventStateCodeList") List<EventSupports.EventStateCode> eventStateCodeList,
                                                       @Param("eventSpaceStateCode") EventSupports.EventSpaceStateCode eventSpaceStateCode);

}

