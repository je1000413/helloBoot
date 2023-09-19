package com.mice.backoffice.c2v_mice_backoffice_api.model.admin;

import lombok.Data;

@Data
public class AdminListRequest {
    private String searchAdminType;
    private String searchAdminValue;
    private int pageSize ;
    private int pageNum ;
}
