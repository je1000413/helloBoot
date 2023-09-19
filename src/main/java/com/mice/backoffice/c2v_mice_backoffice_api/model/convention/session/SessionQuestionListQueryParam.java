package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionListOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionQuestionListQueryParam {
    private SessionQuestionListOrder order;
    // 페이지 넘버
    private int pageNum;

    // 페이지당 목록
    private int pageSize;

    private boolean asc;
}
