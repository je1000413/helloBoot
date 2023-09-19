package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;
import lombok.Data;

@Data
public class EventListForManageSpaceRequest {
    private String searchDateType;
    private String searchDateSDatetime;
    private String searchDateEDatetime;
    private int searchStatusType;
    private String searchEventType;
    private String searchEventValue;
    private int pageSize ;
    private int pageNum ;
}
