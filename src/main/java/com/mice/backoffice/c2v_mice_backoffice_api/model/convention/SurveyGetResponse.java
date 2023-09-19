package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SurveyEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SurveyGetResponse {
    private Long surveyNo;
    private SurveyCode surveyCode;
    private Map<LanguageType, String> surveyPath;
    private SurveyStateCode stateCode;
    private long createUserId;
    private long updateUserId;

    @Builder
    public SurveyGetResponse(Long surveyNo, SurveyCode surveyCode, Map<LanguageType, String> surveyPath, SurveyStateCode stateCode, long createUserId, long updateUserId) {
        this.surveyNo = surveyNo;
        this.surveyCode = surveyCode;
        this.surveyPath = surveyPath;
        this.stateCode = stateCode;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }

    public static SurveyGetResponseBuilder dataBuilder(SurveyEntity se) {
        return builder()
                .surveyNo(se.getSurveyNo())
                .surveyCode(se.getSurveyCode())
                .stateCode(se.getStateCode())
                .createUserId(se.getCreateUserId())
                .updateUserId(se.getUpdateUserId());
    }
}
