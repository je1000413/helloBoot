package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class EventListForPopResponse {

    private Integer rowNum;
    private Long eventId;
    private Object eventName;
    private String eventHosts;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer stateCode;
    private Object stateName;
    private Map<String, Boolean> language;
    private Long loungeNo;
}
