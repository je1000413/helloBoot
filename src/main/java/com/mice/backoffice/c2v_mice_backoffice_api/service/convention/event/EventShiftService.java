package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;

import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;

public interface EventShiftService {
    EventGetResponse get(long eventId) throws Exception;
    EventCreateResponse create(EventShiftCreateRequest req) throws Exception;
    void update(EventShiftUpdateRequest req, long eventId) throws Exception;
    void delete(long eventId) throws Exception;
    void migration(long eventId);
    C2VResponse findEventForShift(EventListRequest req) throws Exception;
}
