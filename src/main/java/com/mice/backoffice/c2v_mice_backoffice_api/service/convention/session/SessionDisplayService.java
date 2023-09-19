package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayUpdateRequest;

import java.util.List;

public interface SessionDisplayService {
    void create(List<SessionDisplayCreateRequest> list, SessionEntity se);
    List<SessionDisplayGetResponse> list(List<SessionDisplayEntity> list);

    void update(List<SessionDisplayUpdateRequest> list, SessionEntity se, DisplayCode displayCode);
}
