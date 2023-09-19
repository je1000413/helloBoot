package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface EventHostRepository extends JpaRepository<EventHostEntity, Long> {
    /*@Query("update EventHostEntity eh set eh")
    public void deleteByNotInHostIds(@Param("useYn") char useYn,
                                     @Param("hostKeys") Collection<String> hostKeys);*/

    public void deleteAllByEventEventId(long eventId);
}
