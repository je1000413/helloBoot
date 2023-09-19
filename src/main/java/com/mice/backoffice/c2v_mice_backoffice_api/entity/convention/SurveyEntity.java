package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "survey")
@Getter
@SuperBuilder
public class SurveyEntity extends JpaBaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "survey_no", unique = true, nullable = false) Long surveyNo;
    private @Column(name = "mapping_id") Long surveyMappingId;
    private @Column(name = "mapping_type") SurveyMappingType surveyMappingType;
    private @Column(name = "survey_code") SurveyCode surveyCode;
    private @Column(name = "survey_path") String surveyPath;
    private @Column(name = "state_code") SurveyStateCode stateCode;
    private @Column(name = "expire_datetime") LocalDateTime expireDatetime;

    public void update (SurveyUpdateRequest req) {
        this.surveyCode = req.getSurveyCode();
        this.stateCode = req.getStateCode();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.stateCode = SurveyStateCode.DELETE;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static SurveyEntityBuilder dataBuilder(SurveyCreateRequest req, long surveyMappingId, SurveyMappingType surveyMappingType) {
        SurveyStateCode surveyStateCode = Objects.requireNonNullElse(req.getStateCode(), SurveyStateCode.USE);

        return builder()
                .surveyMappingId(surveyMappingId)
                .surveyMappingType(surveyMappingType)
                .surveyCode(req.getSurveyCode())
                .stateCode(surveyStateCode)
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }
}
