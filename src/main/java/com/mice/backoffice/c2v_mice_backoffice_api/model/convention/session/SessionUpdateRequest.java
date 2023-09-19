package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionUpdateRequest {
    private long sessionId;
    @NotNull(message = "sessionName is null")
    private Map<LanguageType, String> sessionName;
    private SessionType sessionType;
    @NotNull(message = "sessionDescription is null")
    private Map<LanguageType, String> sessionDescription;
    private int maxMemberCount;
    private Map<LanguageType, String> hallName;
    private long templateId;
    @NotNull(message = "startDatetime is null")
    private LocalDateTime startDatetime;
    @NotNull(message = "endDatetime is null")
    private LocalDateTime endDatetime;
    @NotNull(message = "hallStartDatetime is null")
    private LocalDateTime hallStartDatetime;
    @NotNull(message = "hallEndDatetime is null")
    private LocalDateTime hallEndDatetime;
    private long parentSessionId;
    private char mobileChatYn;
    private char questionYn;
    private SessionStateCode stateCode;
    private SessionOnlineCode onlineCode;

    @NotNull(message = "packages is null")
    private List<PackageUpdateRequest> packages;

    @NotNull(message = "sessionStaffs is null")
    private List<SessionStaffUpdateRequest> sessionStaffs;
    @NotNull(message = "sessionCoverImage is null")
    private List<ScreenUpdateRequest> sessionCoverImages;
    private List<ScreenUpdateRequest> sessionMobileBottomBanners;
    @NotNull(message = "onlineSpaceInfos is null")
    private List<SessionSpaceInfoUpdateRequest> onlineSpaceInfos;
    private List<ScreenUpdateRequest> attachments;

    public Map<LanguageType, String> getSessionName() {
        return Objects.isNull(sessionName) || sessionName.isEmpty() ? null : Collections.unmodifiableMap(sessionName);
    }

    public Map<LanguageType, String> getSessionDescription() {
        return Objects.isNull(sessionDescription) || sessionDescription.isEmpty() ? null : Collections.unmodifiableMap(sessionDescription);
    }
    public Map<LanguageType, String> getHallName() {
        return Objects.isNull(hallName) || hallName.isEmpty() ? null : Collections.unmodifiableMap(hallName);
    }

    public List<SessionStaffUpdateRequest> getSessionStaffs() {
        return Objects.isNull(sessionStaffs) || sessionStaffs.isEmpty() ? null : Collections.unmodifiableList(sessionStaffs);
    }

    public List<ScreenUpdateRequest> getSessionCoverImages() {
        return Objects.isNull(sessionCoverImages) || sessionCoverImages.isEmpty() ? null : Collections.unmodifiableList(sessionCoverImages);
    }

    public List<ScreenUpdateRequest> getSessionMobileBottomBanners() {
        return Objects.isNull(sessionMobileBottomBanners) || sessionMobileBottomBanners.isEmpty() ? null : Collections.unmodifiableList(sessionMobileBottomBanners);
    }

    public List<SessionSpaceInfoUpdateRequest> getOnlineSpaceInfos() {
        return Objects.isNull(onlineSpaceInfos) || onlineSpaceInfos.isEmpty() ? null : Collections.unmodifiableList(onlineSpaceInfos);
    }

    public List<ScreenUpdateRequest> getAttachments() {
        return Objects.isNull(attachments) || attachments.isEmpty() ? null : Collections.unmodifiableList(attachments);
    }

    @Getter
    @NoArgsConstructor
    public static class PackageUpdateInfo {
        private boolean isOnlineOnly;
        @NotNull(message = "packages is null")
        private List<PackageUpdateRequest> packages;

        public List<PackageUpdateRequest> getPackages() {
            return Objects.isNull(packages) || packages.isEmpty() ? null : Collections.unmodifiableList(packages);
        }
    }
}
