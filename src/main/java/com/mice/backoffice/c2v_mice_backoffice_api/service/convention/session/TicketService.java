package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.TicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketUpdateRequest;

import java.util.List;

public interface TicketService {
    TicketEntity create(TicketCreateRequest req, SessionEntity se);
    List<TicketGetDto> list(long sessionId);
    List<TicketEntity> findAllByTicketIds(List<String> ticketIds);
    TicketGetResponse get(List<TicketEntity> list, String ticketId);

    void delete(TicketEntity te);
    List<String> deleteExceptIdList(List<String> ticketIds, long sessionId);
    void update(TicketUpdateRequest req, SessionEntity se);
}
