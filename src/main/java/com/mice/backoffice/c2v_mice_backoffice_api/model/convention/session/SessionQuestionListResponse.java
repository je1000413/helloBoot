package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SessionQuestionListResponse {
    private long totalCount;
    private List<SessionQuestionDetailListResponse> questions;

    @Builder
    public SessionQuestionListResponse(long totalCount, List<SessionQuestionDetailListResponse> questions) {
        this.totalCount = totalCount;
        this.questions = questions;
    }
}
