package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.ProgramType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.SwitchType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminInfoResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionGetResponse;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ProgramGetResponse {
    private long programId;
    private long eventId;
    private Map<LanguageType, Boolean> languageTypeMap;
    private Map<LanguageType, String> eventName;
    private Map<LanguageType, String> programName;
    private Map<LanguageType, String> programDescription;
    private ProgramType programType;
    private SwitchType switchType;

    private List<SurveyGetResponse> surveys;
    private List<SessionGetResponse> sessions;

    private List<ScreenGetResponse> programCoverImages;

    private AdminInfoResponse createUserInfo;
    private AdminInfoResponse updateUserInfo;

    @Builder
    public ProgramGetResponse(long programId, long eventId, Map<LanguageType, Boolean> languageTypeMap, Map<LanguageType, String> eventName, Map<LanguageType, String> programName, Map<LanguageType, String> programDescription, ProgramType programType, SwitchType switchType,
                              List<SurveyGetResponse> surveys, List<SessionGetResponse> sessions, List<ScreenGetResponse> programCoverImages,
                              AdminInfoResponse createUserInfo, AdminInfoResponse updateUserInfo) {
        this.programId = programId;
        this.eventId = eventId;
        this.languageTypeMap = languageTypeMap;
        this.eventName = eventName;
        this.programName = programName;
        this.programDescription = programDescription;
        this.programType = programType;
        this.switchType = switchType;
        this.surveys = surveys;
        this.sessions = sessions;
        this.programCoverImages = programCoverImages;
        this.createUserInfo = createUserInfo;
        this.updateUserInfo = updateUserInfo;
    }

    public static ProgramGetResponseBuilder dataBuilder(ProgramEntity pe) {
        return builder()
                .programId(pe.getProgramId())
                .eventId(pe.getEvent().getEventId())
                .programType(pe.getProgramType())
                .switchType(pe.getSwitchType())
                .createUserInfo(pe.getCreateUser().getUpdateUserInfo(pe.getCreateDatetime()))
                .updateUserInfo(pe.getUpdateUser().getCreateUserInfo(pe.getUpdateDatetime()));
    }
}
