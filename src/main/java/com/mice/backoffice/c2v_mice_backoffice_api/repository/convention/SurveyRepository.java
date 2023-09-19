package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
    List<SurveyEntity> findAllBySurveyMappingIdAndStateCodeNot(long surveyMappingId, SurveyStateCode surveyStateCode);
    void deleteAllBySurveyMappingId(String surveyMappingId);

    @Query("select se " +
            "from SurveyEntity se " +
            "where se.surveyMappingId = :surveyMappingId " +
            "and se.surveyNo not in :surveyNos " +
            "and se.stateCode <> :stateCode")
    List<SurveyEntity> findBySurveyNotSurveyNos(@Param("surveyNos") Collection<Long> surveyNos,
                                                @Param("surveyMappingId") long surveyMappingId,
                                                @Param("stateCode") SurveyStateCode stateCode);
    @Query("select se " +
            "from SurveyEntity se " +
            "where se.surveyMappingId = :surveyMappingId " +
            "and se.stateCode <> :stateCode")
    List<SurveyEntity> findAllBySurveyMappingIdAndStateCode(@Param("surveyMappingId") long surveyMappingId,
                                                            @Param("stateCode") SurveyStateCode stateCode);
}
