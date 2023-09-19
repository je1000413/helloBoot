package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SessionForParentEntity {

    @JsonIgnore
    @Column(name="total_count")
    private Integer totalCount;

    @Column(name="row_num")
    private Integer rowNum;

    @Column(name="event_id")
    private Long eventId;

    @Column(name="event_name")
    private String eventName;

    @Column(name="program_id")
    private Long programId;

    @Column(name="program_name")
    private String programName;

    @Id
    @Column(name="session_id")
    private Long sessionId;

    @Column(name="session_name")
    private String sessionName;

    @Column(name="session_start_datetime")
    private LocalDateTime sessionStartDatetime;

    @Column(name="session_end_datetime")
    private LocalDateTime sessionEndDatetime;
}
