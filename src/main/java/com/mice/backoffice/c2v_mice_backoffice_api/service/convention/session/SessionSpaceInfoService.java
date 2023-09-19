package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionSpaceInfoGetResponse;

import java.util.List;
import java.util.Map;

public interface SessionSpaceInfoService {
    List<SessionSpaceInfoGetResponse> list(Map<DisplayCode, List<ScreenGetResponse>> displayCodeListMap);
}
