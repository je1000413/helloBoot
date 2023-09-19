package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ScreenDisplayRepository extends JpaRepository<ScreenDisplayEntity, Integer> {

    @Query("select sde " +
            "from ScreenDisplayEntity sde " +
            "where sde.screen.screenId = :screenId " +
            "and sde.displaySeq not in :displaySeq " +
            "and sde.stateCode <> :stateCode")
    List<ScreenDisplayEntity> selectByScreenDisplayNotDisplaySeqs(@Param("displaySeq") Collection<Integer> displaySeq,
                                                                  @Param("screenId") long screenId,
                                                                  @Param("stateCode") ScreenDisplayStateCode stateCode);

    @Query("select sde " +
            "from ScreenDisplayEntity sde " +
            "where sde.screen.screenId = :screenId ")
    List<ScreenDisplayEntity> findByScreenId(@Param("screenId") long screenId);
}
