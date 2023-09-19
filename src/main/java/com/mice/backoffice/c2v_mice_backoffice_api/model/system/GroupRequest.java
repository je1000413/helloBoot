package com.mice.backoffice.c2v_mice_backoffice_api.model.system;

import lombok.Data;

@Data
public class GroupRequest {
    private String groupName;
    private Integer roleCode;
    private Integer stateCode;
}
