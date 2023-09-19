package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;


import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.AudienceSortType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionQuestionUserListQueryParam {
    private AudienceSortType sortType;
    private int pageNum;
    private int pageSize;
}
