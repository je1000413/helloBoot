package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;

import java.io.IOException;
import java.util.List;

public interface ScreenService {
    ScreenEntity save(ScreenEntity screenEntity);
    void create(List<ScreenCreateRequest> list, long mappingId, ScreenMappingType type);
    void create(ScreenCreateRequest req, long mappingId, ScreenMappingType type) throws IOException;
    void update(List<ScreenUpdateRequest> list, long mappingId, ScreenMappingType type);
    void update(ScreenUpdateRequest req, long mappingId, ScreenMappingType type) throws IOException;

    List<ScreenGetResponse> list(List<ScreenEntity> list);

    void deleteLounge(long loungeId);

    void updateLounge(List<ScreenUpdateRequest> screenList, long loungeNo, ScreenMappingType lounge) throws IOException;
}
