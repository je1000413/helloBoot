package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AudienceDto {
    private long accountId;
    private String userName;
    private String departmentName;
    private String photoPath;

    @QueryProjection
    public AudienceDto(long accountId, String userName, String departmentName, String photoPath) {
        this.accountId = accountId;
        this.userName = userName;
        this.departmentName = departmentName;
        this.photoPath = photoPath;
    }
}
