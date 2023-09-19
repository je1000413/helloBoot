package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebSocketConfigDto {
    private String deviceId;
    private String appId;
    private long userId;

    @Builder
    public WebSocketConfigDto(String deviceId, String appId, long userId) {
        this.deviceId = deviceId;
        this.appId = appId;
        this.userId = userId;
    }
}
