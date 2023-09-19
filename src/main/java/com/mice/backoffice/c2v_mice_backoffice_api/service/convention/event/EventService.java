package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CodeDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventShiftCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventUpdateRequest;
import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;

import java.util.List;


public interface EventService {
    EventCreateResponse create(EventCreateRequest req) throws Exception;
    EventGetResponse get(long eventId) throws Exception;
    EventDetailGetResponse getDetailPage(long eventId) throws Exception;
    EventEntity findByEventId(long eventId) throws Exception;
    void updateDisplayStateCode(EventPatchMobileDisplayStatusRequest req, long eventId) throws Exception;
    void updateEmergencyTicketStateCode(EventPatchTicketStatusRequest req, long eventId) throws Exception;
    void update(EventUpdateRequest req, long eventId) throws Exception;
    void delete(long eventId) throws Exception;
    List<CodeDto> languageList();

    C2VResponse findEventForDashBoard(EventListRequest req) throws C2VException;
    C2VResponse findEvents(EventListRequest req) throws C2VException;

    C2VResponse findEventsForPop(EventListRequest req) throws C2VException;

    C2VResponse findEventForTimeline(long eventId) throws C2VException;
    C2VResponse findEventForManageSpace(EventListForManageSpaceRequest req) throws C2VException;


}
