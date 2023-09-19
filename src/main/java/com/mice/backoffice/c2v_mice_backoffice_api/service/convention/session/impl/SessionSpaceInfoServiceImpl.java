package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionSpaceInfoGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionSpaceInfoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class SessionSpaceInfoServiceImpl implements SessionSpaceInfoService {
    private List<ScreenGetResponse> list;

    @Override
    @Transactional
    public List<SessionSpaceInfoGetResponse> list(Map<DisplayCode, List<ScreenGetResponse>> displayCodeListMap) {
        List<SessionSpaceInfoGetResponse> result = new ArrayList<>();

        displayCodeListMap.forEach((k,v) -> v.forEach(x -> {
            list = new ArrayList<>();
            listAdd(x);

            SessionSpaceInfoGetResponse sessionSpaceInfo = makeSessionSpaceInfo(k);
            result.add(sessionSpaceInfo);
        }));

        return result;
    }

    public SessionSpaceInfoGetResponse makeSessionSpaceInfo(DisplayCode displayCode) {
        return SessionSpaceInfoGetResponse
                .dataBuilder(displayCode, list)
                .build();
    }

    public void listAdd(ScreenGetResponse sessionDisplay) {
        list.add(sessionDisplay);
    }
}
