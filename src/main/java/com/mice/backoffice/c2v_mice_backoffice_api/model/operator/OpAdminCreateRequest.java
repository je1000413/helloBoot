package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OpAdminCreateRequest {
    private Supports.OperatorAdminStaffType staffType;
    private String accountName;
    private String nickname;
    private String name;
    private String telNo;
    private String mailAddress;
}
