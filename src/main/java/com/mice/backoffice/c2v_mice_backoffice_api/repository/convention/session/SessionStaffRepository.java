package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SessionStaffRepository extends JpaRepository<SessionStaffEntity, Long> {
    List<SessionStaffEntity> findAllByStaffIdNotInAndSessionSessionIdAndUseYn(Collection<Long> sessionStaffIds, long sessionId, char useYn);
    List<SessionStaffEntity> findAllBySessionSessionIdAndUseYn(long sessionId, char useYn);
}
