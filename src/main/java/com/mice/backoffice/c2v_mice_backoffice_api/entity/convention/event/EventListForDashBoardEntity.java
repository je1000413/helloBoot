package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.MapToJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
public class EventListForDashBoardEntity {

    @JsonIgnore
    @Column(name="total_count")
    private Integer totalCount;
    @Column(name="row_num")
    private Integer rowNum;
    @Id
    @Column(name="event_id")
    private Long eventId;

    @Convert(converter = MapToJsonConverter.class)
    @Column(name="event_name")
    private Map<String, Object> eventName;
    @Column(name="event_hosts")
    private String eventHosts;
    @Column(name="event_start_datetime")
    private LocalDateTime eventStartDatetime;
    @Column(name="event_end_datetime")
    private LocalDateTime eventEndDatetime;
    @Column(name="lounge_start_datetime")
    private LocalDateTime loungeStartDatetime;
    @Column(name="lounge_end_datetime")
    private LocalDateTime loungeEndDatetime;
    @Column(name="program_cnt")
    private Integer programCnt;
    @Column(name="session_cnt")
    private Integer sessionCnt;
    @Column(name="package_cnt")
    private Integer packageCnt;
    @Column(name="state_code")
    private Integer stateCode;
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="state_name")
    private Map<String, Object> stateName;
    @Column(name="lounge_no")
    private Long loungeNo;
}
