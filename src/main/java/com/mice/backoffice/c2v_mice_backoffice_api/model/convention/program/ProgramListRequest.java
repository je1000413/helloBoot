package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program;

import lombok.Data;

@Data
public class ProgramListRequest {
    private String searchDateType;
    private String searchDateSDatetime;
    private String searchDateEDatetime;
    private int searchProgramTypeCode;
    private String searchProgramType;
    private String searchProgramValue;
    private String searchDomainType;
    private String searchDomainValue;
    private int pageSize ;
    private int pageNum ;
}