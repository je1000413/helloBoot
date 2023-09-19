package com.mice.backoffice.c2v_mice_backoffice_api.model.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GroupJoinRequest {
    private String groupId;
    private String userId;
    private String appId;
    private String deviceId;
    private String userName;

    @Builder
    public GroupJoinRequest(String groupId, String userId, String appId, String deviceId, String userName) {
        this.groupId = groupId;
        this.userId = userId;
        this.appId = appId;
        this.deviceId = deviceId;
        this.userName = userName;
    }
}
