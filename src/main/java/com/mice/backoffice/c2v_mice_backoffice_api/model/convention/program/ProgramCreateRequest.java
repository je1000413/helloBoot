package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.ProgramType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.SwitchType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ProgramCreateRequest {
    @DecimalMin(value = "1", message = "invalid eventId")
    private long eventId;
    @NotNull(message = "programName is null")
    private Map<LanguageType, String> programName;
    @NotNull(message = "programDescription is null")
    private Map<LanguageType, String> programDescription;
    private ProgramType programType;
    private SwitchType switchType;
    private List<SurveyCreateRequest> surveys;
    @NotNull(message = "sessions is null")
    private List<SessionCreateRequest> sessions;
    @NotNull(message = "programCoverImages is null")
    private List<ScreenCreateRequest> programCoverImages;

    public List<SessionCreateRequest> getSessions() {
        return Objects.isNull(sessions) || sessions.isEmpty() ? null : Collections.unmodifiableList(sessions);
    }

    public List<SurveyCreateRequest> getSurveys() {
        return Objects.isNull(surveys) || surveys.isEmpty() ? null : Collections.unmodifiableList(surveys);
    }

    public List<ScreenCreateRequest> getProgramCoverImages() {
        return Objects.isNull(programCoverImages) || programCoverImages.isEmpty() ? null : Collections.unmodifiableList(programCoverImages);
    }

    public Map<LanguageType, String> getProgramName() {
        return Objects.isNull(programName) || programName.isEmpty() ? null : Collections.unmodifiableMap(programName);

    }
    public Map<LanguageType, String> getProgramDescription() {
        return Objects.isNull(programDescription) || programDescription.isEmpty() ? null : Collections.unmodifiableMap(programDescription);
    }
}
