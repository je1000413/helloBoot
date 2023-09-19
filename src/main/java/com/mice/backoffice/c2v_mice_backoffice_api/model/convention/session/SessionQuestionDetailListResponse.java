package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.SessionQuestionListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionQuestionDetailListResponse {
    private int questionSeq;
    private AccountResponse accountInfo;
    private String question;
    private String description;
    private SessionQuestionStateCode stateCode;

    @Builder
    public SessionQuestionDetailListResponse(int questionSeq, AccountResponse accountInfo, String question, String description, SessionQuestionStateCode stateCode) {
        this.questionSeq = questionSeq;
        this.accountInfo = accountInfo;
        this.question = question;
        this.description = description;
        this.stateCode = stateCode;
    }

    public static SessionQuestionDetailListResponse makeInstance(SessionQuestionListDto dto) {
        return builder()
                .questionSeq(dto.getQuestionSeq())
                .accountInfo(AccountResponse.makeInstance(dto))
                .question(dto.getQuestion())
                .description(dto.getDescription())
                .stateCode(dto.getStateCode())
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AccountResponse {
        private String accountName;
        private String departmentName;
        private int likeCount;
        private int viewCount;

        @Builder
        public AccountResponse(String accountName, String departmentName, int likeCount, int viewCount) {
            this.accountName = accountName;
            this.departmentName = departmentName;
            this.likeCount = likeCount;
            this.viewCount = viewCount;
        }

        public static AccountResponse makeInstance(SessionQuestionListDto dto) {
            String accountName = String.format("%s%s(%s)", dto.getSurName(), dto.getGivenName(), dto.getMiddleName());

            return builder()
                    .accountName(accountName)
                    .departmentName(dto.getDepartmentName())
                    .likeCount(dto.getLikeCount())
                    .viewCount(dto.getViewCount())
                    .build();
        }
    }
}
