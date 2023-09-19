package com.mice.backoffice.c2v_mice_backoffice_api.model.system;

import lombok.*;

@Data
public class MenuAuthorityRequest {
    private int menuSeq;
    private int roleCode;
    private int authorityCode;
}