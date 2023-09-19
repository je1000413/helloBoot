package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.ProgramType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.SwitchType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionUpdateRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ProgramUpdateRequest {
    @DecimalMin(value = "1", message = "invalid eventId")
    private long eventId;
    @NotNull(message = "programName is null")
    private Map<LanguageType, String> programName;
    @NotNull(message = "programDescription is null")
    private Map<LanguageType, String> programDescription;
    private ProgramType programType;
    private SwitchType switchType;

    private List<ScreenUpdateRequest> programCoverImages;

    private List<SurveyUpdateRequest> surveys;
    private List<SessionUpdateRequest> sessions;



    public List<SessionUpdateRequest> getSessions() {
        return Objects.isNull(sessions) || sessions.isEmpty() ? null : Collections.unmodifiableList(sessions);
    }

    public List<SurveyUpdateRequest> getSurveys() {
        return Objects.isNull(surveys) || surveys.isEmpty() ? null : Collections.unmodifiableList(surveys);
    }

    public List<ScreenUpdateRequest> getProgramCoverImages() {
        return Objects.isNull(programCoverImages) || programCoverImages.isEmpty() ? null : Collections.unmodifiableList(programCoverImages);
    }

    public Map<LanguageType, String> getProgramName() {
        return Objects.isNull(programName) || programName.isEmpty() ? null : Collections.unmodifiableMap(programName);

    }
    public Map<LanguageType, String> getProgramDescription() {
        return Objects.isNull(programDescription) || programDescription.isEmpty() ? null : Collections.unmodifiableMap(programDescription);
    }
}
