package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SessionQuestionUserDetailListResponse {
    private String userName;
    private String departmentName;
    private String photoPath;
    private LocalDateTime createDateTime;

    @Builder
    public SessionQuestionUserDetailListResponse(String userName, String departmentName, String photoPath, LocalDateTime createDateTime) {
        this.userName = userName;
        this.departmentName = departmentName;
        this.photoPath = photoPath;
        this.createDateTime = createDateTime;
    }
}
