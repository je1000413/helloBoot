package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OpAdminListRequest {

    private Supports.OperatorAdminSearchNameAndId operatorAdminSearchNameAndId;
    private String nameAndId;
    private Supports.OperatorAdminStaffType staffType;
    private int pageNum;
    private int pageSize;

}
