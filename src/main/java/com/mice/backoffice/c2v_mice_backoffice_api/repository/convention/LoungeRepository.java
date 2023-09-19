package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LoungeStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity.LoungeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoungeRepository extends JpaRepository<LoungeEntity, LoungeId> {
    List<LoungeEntity> findAllByPkEventIdAndStateCodeNot(long eventId, LoungeStateCode stateCode);

    Optional<LoungeEntity> findByPkLoungeNo(int loungeNo);

    @Query(value = "SELECT nextval('lounge')", nativeQuery = true)
    int findLoungeNo();

    @Query(value = "select le " +
            "from LoungeEntity le " +
            "where le.pk.eventId = :eventId " +
            "and le.loungeType = :loungeType " +
            "and le.stateCode <> 3 " +
            "order by le.pk.eventId desc")
    List<LoungeEntity> findByEventIdAndLoungeType(Long eventId, Supports.LoungeType loungeType);

    @Query(value = "select le " +
            "from LoungeEntity le " +
            "where le.pk.eventId = (select lele.pk.eventId from LoungeEntity lele where lele.pk.loungeNo = :loungeNo) " +
            "and le.stateCode <> 3 ")
    List<LoungeEntity> findListByLoungeNo(int loungeNo);
}
