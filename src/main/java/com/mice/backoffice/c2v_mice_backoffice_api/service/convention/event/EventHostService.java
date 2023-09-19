package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventHostService {
    void create(List<EventHostCreateRequest> list, long eventId) throws Exception;
    EventHostGetResponse getEventHostResponse(List<EventHostEntity> list);
    Page<EventHostEntity> list(Pageable pageable);
    void update(List<EventHostUpdateRequest> list, long eventId) throws Exception;
}
