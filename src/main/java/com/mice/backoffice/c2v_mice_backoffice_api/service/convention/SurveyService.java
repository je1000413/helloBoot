package com.mice.backoffice.c2v_mice_backoffice_api.service.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyUpdateRequest;

import java.util.List;

public interface SurveyService {
    void create(List<SurveyCreateRequest> list, long surveyMappingId, Supports.SurveyMappingType surveyMappingType);
    void update(List<SurveyUpdateRequest> list, long surveyMappingId, Supports.SurveyMappingType surveyMappingType);

    List<SurveyGetResponse> listBySurveyMappingId(long surveyMappingId);
}
