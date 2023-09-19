package com.mice.backoffice.c2v_mice_backoffice_api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminInfoResponse {
    private long userId;
    private String accountName;
    private String name;
    private LocalDateTime dateTime;

    @Builder
    public AdminInfoResponse(long userId, String accountName, String name, LocalDateTime dateTime) {
        this.userId = userId;
        this.accountName = accountName;
        this.name = name;
        this.dateTime = dateTime;
    }
}
