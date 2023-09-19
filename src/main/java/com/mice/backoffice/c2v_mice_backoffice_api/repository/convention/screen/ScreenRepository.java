package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ScreenRepository extends JpaRepository<ScreenEntity, Long> {
    @Query("select se " +
            "from ScreenEntity se " +
            "where se.mappingId = :mappingId " +
            "and se.screenId not in :screenIds " +
            "and se.displayCode = :displayCode " +
            "and se.stateCode <> 5")
    List<ScreenEntity> findAllByScreenIdNot(@Param("mappingId") long mappingId,
                                            @Param("screenIds") Collection<Long> screenIds,
                                            @Param("displayCode") DisplayCode displayCode);

    @Query("select se " +
            "from ScreenEntity se " +
            "where se.mappingId = :mappingId " +
            "and se.screenId not in :screenIds " +
            "and se.displayCode = :displayCode " +
            "and (se.spaceObjectId = 0 or se.spaceObjectId is null)" +
            "and se.stateCode <> 5")
    List<ScreenEntity> findAllByScreenIdNotAndSpaceObjectIdIsNull(@Param("mappingId") long mappingId,
                                                                  @Param("screenIds") Collection<Long> screenIds,
                                                                  @Param("displayCode") DisplayCode displayCode);


    @Query("select se " +
            "from ScreenEntity se " +
            "where se.mappingId = :mappingId " +
            "and se.stateCode <> 5")
    List<ScreenEntity> findAllByEventId(@Param("mappingId") long mappingId);
}
