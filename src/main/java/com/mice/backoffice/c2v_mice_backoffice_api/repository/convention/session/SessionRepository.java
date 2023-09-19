package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findAllBySessionIdNotInAndProgramProgramId(Collection<Long> sessionIds, long programId);

    List<SessionEntity> findBySpaceIdAndStateCodeNot(String spaceId, SessionStateCode stateCode);

    List<SessionEntity> findBySessionIdNotAndSpaceIdAndStateCodeNot(Long sessionId, String spaceId, SessionStateCode stateCode);
}
