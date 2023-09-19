package com.mice.backoffice.c2v_mice_backoffice_api.model.system;

import lombok.Data;

@Data
public class MenuRequest {
    private int parentMenuSeq;
    private int menuNo;
    private String menuName;
    private String linkAddress;
    private int menuType;
}