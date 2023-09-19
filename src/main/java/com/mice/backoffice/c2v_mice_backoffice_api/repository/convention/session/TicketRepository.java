package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity.TicketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, TicketId> {
    @Query(value = "select nextval('ticket')", nativeQuery = true)
    String findTicketId();

    @Query(value = "select t " +
            "from TicketEntity t " +
            "where t.ticketId.ticketId not in :ticketIds " +
            "and t.ticketId.session.sessionId = :sessionId")
    List<TicketEntity> findAllByTicketIdNotInSessionId(@Param("ticketIds") Collection<String> ticketIds,
                                                       @Param("sessionId") long sessionId);
}
