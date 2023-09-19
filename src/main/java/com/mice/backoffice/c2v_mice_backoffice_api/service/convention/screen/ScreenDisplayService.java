package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayUpdateRequest;

import java.io.IOException;
import java.util.List;

public interface ScreenDisplayService {
    void create(List<ScreenDisplayCreateRequest> list, ScreenEntity se) throws IOException;
    void update(List<ScreenDisplayUpdateRequest> list, ScreenEntity se) throws IOException;
    List<ScreenDisplayGetResponse> list(List<ScreenDisplayEntity> list);
    ScreenDisplayEntity save(ScreenDisplayEntity screenDisplayEntity);
    void deleteLounge(ScreenEntity screenEntity, long screenId);
}