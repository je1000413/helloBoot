package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor
public class SurveyCreateRequest {
    private SurveyCode surveyCode;
    private SurveyStateCode stateCode;
    @NotNull(message = "surveyPath is null")
    private Map<LanguageType, String> surveyPath;


    @Builder
    public SurveyCreateRequest(SurveyCode surveyCode, SurveyStateCode stateCode, Map<LanguageType, String> surveyPath) {
        this.surveyCode = surveyCode;
        this.stateCode = stateCode;
        this.surveyPath = surveyPath;
    }

    public Map<LanguageType, String> getSurveyPath() {
        return Objects.isNull(surveyPath) || surveyPath.isEmpty() ? null : Collections.unmodifiableMap(surveyPath);
    }

    public static SurveyCreateRequest makeCreateRequest(SurveyUpdateRequest req) {
        Map<LanguageType, String> map = Objects.requireNonNullElseGet(req.getSurveyPath(), HashMap::new);

        if (map.isEmpty())
            map.put(LanguageType.KO_KR, "");

        return builder()
                .surveyCode(req.getSurveyCode())
                .surveyPath(map)
                .build();
    }
}
