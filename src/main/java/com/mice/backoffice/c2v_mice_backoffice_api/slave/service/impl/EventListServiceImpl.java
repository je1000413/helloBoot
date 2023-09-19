package com.mice.backoffice.c2v_mice_backoffice_api.slave.service.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.slave.entity.EventListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.slave.repository.EventListRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.slave.service.EventListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventListServiceImpl implements EventListService {

    private final EventListRepository eventListRepository;
    
    @Override
    @Transactional
    public C2VResponse findEvents(EventListRequest req) throws C2VException {

        List<EventListEntity> list = eventListRepository.findEventForList(
                RequestUtils.getCurAdminId(),
                req.getSearchDateType(),
                req.getSearchDateSDatetime(),
                req.getSearchDateEDatetime(),
                req.getSearchStatusType(),
                req.getSearchEventType(),
                req.getSearchEventValue(),
                req.getSearchDomainType(),
                req.getSearchDomainValue(),
                req.getPageSize(),
                req.getPageNum(),
                req.getLoungeYn()
        );

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }
}
