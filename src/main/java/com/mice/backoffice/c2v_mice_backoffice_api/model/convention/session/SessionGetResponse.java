package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
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
public class SessionGetResponse {
    private long sessionId;
    private Map<LanguageType, String> sessionName;
    private SessionType sessionType;
    private Map<LanguageType, String> sessionDescription;
    private int maxMemberCount;
    private Map<LanguageType, String> hallName;
    private String spaceId;
    private long templateId;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private LocalDateTime hallStartDatetime;
    private LocalDateTime hallEndDatetime;
    private long parentSessionId;
    private Map<LanguageType, String> parentSessionName;
    private SessionStateCode stateCode;
    private SessionOnlineCode onlineCode;
    private char questionYn;
    private char mobileChatYn;
    private long createUserId;
    private long updateUserId;

    private List<SessionStaffGetResponse> sessionStaffs;
    private List<ScreenGetResponse> sessionCoverImages;
    private List<ScreenGetResponse> sessionMobileBottomBanners;
    private List<SessionSpaceInfoGetResponse> onlineSpaceInfos;
    private List<ScreenGetResponse> attachments;

    private List<PackageGetResponse> packages;

    @Builder
    public SessionGetResponse(long sessionId, Map<LanguageType, String> sessionName, SessionType sessionType, Map<LanguageType, String> sessionDescription,
                              int maxMemberCount, Map<LanguageType, String> hallName, String spaceId, long templateId, LocalDateTime startDatetime, LocalDateTime endDatetime, LocalDateTime hallStartDatetime,
                              LocalDateTime hallEndDatetime, long parentSessionId, Map<LanguageType, String> parentSessionName, SessionStateCode stateCode, SessionOnlineCode onlineCode, char questionYn, char mobileChatYn, long createUserId, long updateUserId, List<SessionStaffGetResponse> sessionStaffs, List<ScreenGetResponse> sessionCoverImages,
                              List<ScreenGetResponse> sessionMobileBottomBanners, List<SessionSpaceInfoGetResponse> onlineSpaceInfos, List<ScreenGetResponse> attachments, List<PackageGetResponse> packages ) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionType = sessionType;
        this.sessionDescription = sessionDescription;
        this.maxMemberCount = maxMemberCount;
        this.hallName  = hallName;
        this.spaceId = spaceId;
        this.templateId = templateId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.hallStartDatetime = hallStartDatetime;
        this.hallEndDatetime = hallEndDatetime;
        this.parentSessionId = parentSessionId;
        this.parentSessionName = parentSessionName;
        this.stateCode = stateCode;
        this.onlineCode = onlineCode;
        this.questionYn = questionYn;
        this.mobileChatYn = mobileChatYn;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.sessionStaffs = sessionStaffs;
        this.sessionCoverImages = sessionCoverImages;
        this.sessionMobileBottomBanners = sessionMobileBottomBanners;
        this.onlineSpaceInfos = onlineSpaceInfos;
        this.attachments = attachments;
        this.packages = packages;
    }

    public static SessionGetResponseBuilder dataBuilder(SessionEntity se) {
        return builder()
                .sessionId(se.getSessionId())
                .sessionType(se.getSessionType())
                .maxMemberCount(se.getMaxMemberCount())
                .spaceId(se.getSpaceId())
                .templateId(se.getTemplateId())
                .startDatetime(se.getStartDatetime())
                .endDatetime(se.getEndDatetime())
                .hallStartDatetime(se.getHallStartDatetime())
                .hallEndDatetime(se.getHallEndDatetime())
                .parentSessionId(se.getParentSessionId())
                .stateCode(se.getStateCode())
                .onlineCode(se.getOnlineCode())
                .questionYn(se.getQuestionYn())
                .mobileChatYn(se.getMobileChatYn())
                .createUserId(se.getCreateUserId())
                .updateUserId(se.getUpdateUserId());
    }
}
