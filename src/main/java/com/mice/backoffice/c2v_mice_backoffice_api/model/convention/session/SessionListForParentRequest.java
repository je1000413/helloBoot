package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import lombok.Data;

@Data
public class SessionListForParentRequest {
    private long eventId;
    private int searchProgramTypeCode;
    private String searchSessionType;
    private String searchSessionValue;
    private int pageSize ;
    private int pageNum ;
}
