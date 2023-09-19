package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class EventShiftListEntity {

    @JsonIgnore
    @Column(name="total_count")
    private Integer totalCount;
    @Column(name="row_num")
    private Integer rowNum;
    @Id
    @Column(name="event_id")
    private Long eventId;
    @Column(name="event_name")
    private String eventName;
    @Column(name="event_hosts")
    private String eventHosts;
    @Column(name="event_start_datetime")
    private LocalDateTime eventStartDatetime;
    @Column(name="event_end_datetime")
    private LocalDateTime eventEndDatetime;
    @Column(name="create_datetime")
    private LocalDateTime createDatetime;
    @Column(name="update_datetime")
    private LocalDateTime updateDatetime;
    @Column(name="state_code")
    private Integer stateCode;
    @Column(name="state_name")
    private String stateName;
}
