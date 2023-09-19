package com.mice.backoffice.c2v_mice_backoffice_api.slave.service;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventListRequest;

public interface EventListService {
    C2VResponse findEvents(EventListRequest req) throws C2VException;
}
