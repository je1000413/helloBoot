package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionListForParentRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionUpdateRequest;

import java.util.List;

public interface SessionService {
    void create(SessionCreateRequest sr, ProgramEntity pe);
    void create(List<SessionCreateRequest> list, ProgramEntity pe);
    List<SessionEntity> findAllBySessionIds(List<Long> sessionIds);

    void update(List<SessionUpdateRequest> list, ProgramEntity pe);
    List<SessionGetResponse> list(List<SessionEntity> list) throws C2VException;
    C2VResponse findSessionForParent(SessionListForParentRequest req) throws C2VException;
}
