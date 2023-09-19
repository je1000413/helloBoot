package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SurveyUpdateRequest {
    @NotBlank(message = "surveyNo is required")
    private Long surveyNo;
    private SurveyCode surveyCode;
    @NotNull(message = "surveyPath is null")
    private Map<LanguageType, String> surveyPath;
    private SurveyStateCode stateCode;

    @Builder
    public SurveyUpdateRequest(Long surveyNo, SurveyCode surveyCode, Map<LanguageType, String> surveyPath, SurveyStateCode stateCode) {
        this.surveyNo = surveyNo;
        this.surveyCode = surveyCode;
        this.surveyPath = surveyPath;
        this.stateCode = stateCode;
    }

    public Map<LanguageType, String> getSurveyPath() {
        return Objects.isNull(surveyPath) || surveyPath.isEmpty() ? null : Collections.unmodifiableMap(surveyPath);
    }
}
