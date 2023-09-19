package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionStateCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SessionQuestionListDto {
    private int questionSeq;
    private String surName;
    private String givenName;
    private String middleName;
    private String departmentName;
    private int likeCount;
    private int viewCount;
    private String question;
    private String description;
    private SessionQuestionStateCode stateCode;

    @QueryProjection
    public SessionQuestionListDto(int questionSeq, String surName, String givenName, String middleName, String departmentName, int likeCount, int viewCount, String question, String description, SessionQuestionStateCode stateCode) {
        this.questionSeq = questionSeq;
        this.surName = surName;
        this.givenName = givenName;
        this.middleName = middleName;
        this.departmentName = departmentName;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.question = question;
        this.description = description;
        this.stateCode = stateCode;
    }
}
