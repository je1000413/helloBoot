package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OpAdminUpdateRequest {
    private Supports.OperatorAdminStaffType staffType;
    private String nickname;
    private String name;
    private String telNo;
    private String mailAddress;
    private Supports.OperatorAdminUseYn useYn;
}
