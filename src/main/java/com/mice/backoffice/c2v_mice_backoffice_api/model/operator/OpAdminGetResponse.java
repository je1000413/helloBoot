package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OpAdminGetResponse {
    private Supports.OperatorAdminStaffType staffType;
    private long adminId;
    private String accountName;
    private String nickname;
    private String name;
    private LocalDateTime createDatetime;
    private String telNo;
    private LocalDateTime lastLoginDatetime;
    private String mailAddress;
    private Supports.OperatorAdminUseYn useYn;
    @Builder
    public OpAdminGetResponse(AdminEntity ae, Supports.OperatorAdminStaffType staffType, Supports.OperatorAdminUseYn useYn) {
        this.staffType = staffType;
        this.adminId = ae.getAdminId();
        this.accountName = ae.getAccountName();
        this.nickname = ae.getNickname();
        this.name = ae.getName();
        this.createDatetime = ae.getCreateDatetime();
        this.telNo = ae.getTelNo();
        this.lastLoginDatetime = ae.getLastLoginDatetime();
        this.mailAddress = ae.getMailAddress();
        this.useYn = useYn;
    }

}
