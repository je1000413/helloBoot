package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EventListRequest {
    private String searchDateType;
    private String searchDateSDatetime;
    private String searchDateEDatetime;
    private int searchStatusType;
    private String searchEventType;
    private String searchEventValue;
    private String searchDomainType;
    private String searchDomainValue;
    private int pageSize ;
    private int pageNum ;
    private String loungeYn;
}
