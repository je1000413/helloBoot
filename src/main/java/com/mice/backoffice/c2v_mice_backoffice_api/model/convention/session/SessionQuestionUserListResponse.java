package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SessionQuestionUserListResponse {
    private long totalCount;
    private List<SessionQuestionUserDetailListResponse> questionUsers;

    @Builder
    public SessionQuestionUserListResponse(long totalCount, List<SessionQuestionUserDetailListResponse> questionUsers) {
        this.totalCount = totalCount;
        this.questionUsers = questionUsers;
    }
}
