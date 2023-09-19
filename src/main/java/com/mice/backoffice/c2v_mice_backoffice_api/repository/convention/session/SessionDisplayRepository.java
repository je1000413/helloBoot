package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity.SessionDisplayId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SessionDisplayRepository extends JpaRepository<SessionDisplayEntity, SessionDisplayId> {
    @Query(value = "select nextval('sessionDisplay')", nativeQuery = true)
    int findSessionDisplayId();
    @Query(value = "select sde " +
            "from SessionDisplayEntity sde " +
            "where sde.sessionDisplayId.displaySeq not in :displaySeqs " +
            "and sde.stateCode <> :stateCode " +
            "and sde.sessionDisplayId.session.sessionId = :sessionId " +
            "and sde.displayCode = :displayCode")
    List<SessionDisplayEntity> findAllByDisplaySeqNotInAndDisplayCode(@Param("displaySeqs") Collection<Long> displaySeqs,
                                                                      @Param("displayCode") DisplayCode displayCode,
                                                                      @Param("sessionId") long sessionId,
                                                                      @Param("stateCode") SessionDisplayStateCode stateCode);
}
