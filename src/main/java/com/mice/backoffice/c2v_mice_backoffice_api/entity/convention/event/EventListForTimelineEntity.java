package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event;

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
public class EventListForTimelineEntity {
    @Column(name="event_id")
    private Long eventId;
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="event_name")
    private Map<String, Object> eventName;
    @Column(name="event_start_datetime")
    private String eventStartDatetime;
    @Column(name="event_end_datetime")
    private String eventEndDatetime;
    @Column(name="program_id")
    private Long programId;
    @Column(name="program_type")
    private Integer programType;
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="program_type_name")
    private Map<String, Object> programTypeName;
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="program_name")
    private Map<String, Object> programName;

    @Id
    @Column(name="session_id")
    private Long sessionId;
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="session_name")
    private Map<String, Object> sessionName;
    @Column(name="staff_name")
    private String staffName;
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="hall_name")
    private Map<String, Object> hallName;
    @Column(name="event_day")
    private String eventDay;
    @Column(name="session_start_timeline")
    private String sessionStartTimeline;
    @Column(name="session_end_timeline")
    private String sessionEndTimeline;
    @Column(name="session_min_diff")
    private Integer sessionNinDiff;
}
