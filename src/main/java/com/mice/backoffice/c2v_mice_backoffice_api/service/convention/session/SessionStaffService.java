package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionStaffEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionStaffCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionStaffGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionStaffUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionUpdateRequest;

import java.util.List;

public interface SessionStaffService {
    void create(List<SessionStaffCreateRequest> list, SessionEntity se);
    List<SessionStaffGetResponse> list(List<SessionStaffEntity> list);
    void update(List<SessionStaffUpdateRequest> list, SessionEntity se);
}
