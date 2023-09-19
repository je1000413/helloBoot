package com.mice.backoffice.c2v_mice_backoffice_api.model.admin;


import lombok.Data;

@Data
public class AdminRequest {
    private String accountPassword;
    private String accountName;
    private String name;
    private Integer domainId;
    private String mailAddress;
    private String telNo;
    private Integer groupId;
    private Integer stateCode;

    private String nickname;
}