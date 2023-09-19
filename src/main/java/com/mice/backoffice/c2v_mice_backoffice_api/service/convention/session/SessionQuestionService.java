package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionListResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionStateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionUserListQueryParam;

public interface SessionQuestionService {
    SessionQuestionListResponse list(SessionQuestionListQueryParam param, long sessionId) throws Exception;
    void list(SessionQuestionUserListQueryParam param, long sessionId) throws Exception;
    void changeState(SessionQuestionStateRequest stateCode, long sessionId, int questionSeq) throws Exception;
}
