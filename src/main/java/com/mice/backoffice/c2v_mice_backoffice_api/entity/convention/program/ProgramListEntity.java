package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ProgramListEntity {
    @JsonIgnore
    @Column(name="total_count")
    private Integer totalCount;
    @Column(name="row_num")
    private Integer rowNum;

    @Column(name="event_id")
    private Long eventId;
    @Column(name="event_name")
    private String eventName;

    @Id
    @Column(name="program_id")
    private Long programId;

    @Column(name="program_name")
    private String programName;

    @Column(name="program_type")
    private Integer programType;

    @Column(name="program_type_name")
    private String programTypeName;

    @Column(name="session_cnt")
    private Integer sessionCnt;
    @Column(name="program_start_datetime")
    private LocalDateTime programStartDatetime;
    @Column(name="program_end_datetime")
    private LocalDateTime programEndDatetime;
    @Column(name="create_datetime")
    private LocalDateTime createDatetime;
    @Column(name="update_datetime")
    private LocalDateTime updateDatetime;

}
