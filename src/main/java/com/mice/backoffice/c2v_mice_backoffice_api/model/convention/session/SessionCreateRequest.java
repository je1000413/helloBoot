package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.*;

@Getter
@NoArgsConstructor
public class SessionCreateRequest {
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
    private List<PackageCreateRequest> packages;

    @NotNull(message = "sessionStaffs is null")
    private List<SessionStaffCreateRequest> sessionStaffs;
    @NotNull(message = "sessionCoverImage is null")
    private List<ScreenCreateRequest> sessionCoverImages;
    private List<ScreenCreateRequest> sessionMobileBottomBanners;
    @NotNull(message = "onlineSpaceInfos is null")
    private List<SessionSpaceInfoCreateRequest> onlineSpaceInfos;
    private List<ScreenCreateRequest> attachments;

    @Builder
    public SessionCreateRequest(Map<LanguageType, String> sessionName, SessionType sessionType, Map<LanguageType, String> sessionDescription, int maxMemberCount,
                                Map<LanguageType, String> hallName, long templateId, LocalDateTime startDatetime, LocalDateTime endDatetime, LocalDateTime hallStartDatetime, LocalDateTime hallEndDatetime,
                                long parentSessionId, char mobileChatYn, char questionYn, SessionStateCode stateCode, SessionOnlineCode onlineCode, List<PackageCreateRequest> packages, List<SessionStaffCreateRequest> sessionStaffs, List<ScreenCreateRequest> sessionMobileBottomBanners, List<ScreenCreateRequest> sessionCoverImages,
                                List<SessionSpaceInfoCreateRequest> onlineSpaceInfos, List<ScreenCreateRequest> attachments) {
        this.sessionName = sessionName;
        this.sessionType = sessionType;
        this.sessionDescription = sessionDescription;
        this.maxMemberCount = maxMemberCount;
        this.hallName = hallName;
        this.templateId = templateId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.hallStartDatetime = hallStartDatetime;
        this.hallEndDatetime = hallEndDatetime;
        this.parentSessionId = parentSessionId;
        this.questionYn = questionYn;
        this.mobileChatYn = mobileChatYn;
        this.stateCode = stateCode;
        this.onlineCode = onlineCode;
        this.packages = packages;
        this.sessionStaffs = sessionStaffs;
        this.sessionCoverImages = sessionCoverImages;
        this.sessionMobileBottomBanners = sessionMobileBottomBanners;
        this.onlineSpaceInfos = onlineSpaceInfos;
        this.attachments = attachments;
    }

    public Map<LanguageType, String> getSessionName() {
        return Objects.isNull(sessionName) || sessionName.isEmpty() ? null : Collections.unmodifiableMap(sessionName);
    }

    public Map<LanguageType, String> getSessionDescription() {
        return Objects.isNull(sessionDescription) || sessionDescription.isEmpty() ? null : Collections.unmodifiableMap(sessionDescription);
    }
    public Map<LanguageType, String> getHallName() {
        return Objects.isNull(hallName) || hallName.isEmpty() ? null : Collections.unmodifiableMap(hallName);
    }

    public List<SessionStaffCreateRequest> getSessionStaffs() {
        return Objects.isNull(sessionStaffs) || sessionStaffs.isEmpty() ? null : Collections.unmodifiableList(sessionStaffs);
    }

    public List<ScreenCreateRequest> getSessionCoverImages() {
        return Objects.isNull(sessionCoverImages) || sessionCoverImages.isEmpty() ? null : Collections.unmodifiableList(sessionCoverImages);
    }

    public List<ScreenCreateRequest> getSessionMobileBottomBanners() {
        return Objects.isNull(sessionMobileBottomBanners) || sessionMobileBottomBanners.isEmpty() ? null : Collections.unmodifiableList(sessionMobileBottomBanners);
    }

    public List<SessionSpaceInfoCreateRequest> getOnlineSpaceInfo() {
        return Objects.isNull(onlineSpaceInfos) || onlineSpaceInfos.isEmpty() ? null : Collections.unmodifiableList(onlineSpaceInfos);
    }

    public List<ScreenCreateRequest> getAttachments() {
        return Objects.isNull(attachments) || attachments.isEmpty() ? null : Collections.unmodifiableList(attachments);
    }

    public static SessionCreateRequest makeCreateRequest(SessionUpdateRequest req) {
        List<PackageCreateRequest> packageList = Objects.isNull(req.getPackages()) ? null : req.getPackages().stream().map(PackageCreateRequest::makeCreateRequest).toList();
        List<ScreenCreateRequest> sessionCoverImageList = Objects.isNull(req.getSessionCoverImages()) ? null : req.getSessionCoverImages().stream().map(ScreenCreateRequest::makeCreateRequest).toList();
        List<SessionSpaceInfoCreateRequest> onlineSpaceList = Objects.isNull(req.getOnlineSpaceInfos()) ? null : req.getOnlineSpaceInfos().stream().map(SessionSpaceInfoCreateRequest::makeCreateRequest).toList();
        List<ScreenCreateRequest> attachmentList = Objects.isNull(req.getAttachments()) ? null : req.getAttachments().stream().map(ScreenCreateRequest::makeCreateRequest).toList();
        List<ScreenCreateRequest> mobileBottomBannerList = Objects.isNull(req.getSessionMobileBottomBanners()) ? null : req.getSessionMobileBottomBanners().stream().map(ScreenCreateRequest::makeCreateRequest).toList();
        List<SessionStaffCreateRequest> sessionStaffList = Objects.isNull(req.getSessionStaffs()) ? null : req.getSessionStaffs().stream().map(SessionStaffCreateRequest::makeCreateRequest).toList();

        return builder()
                .sessionName(req.getSessionName())
                .sessionType(req.getSessionType())
                .sessionDescription(req.getSessionDescription())
                .maxMemberCount(req.getMaxMemberCount())
                .hallName(req.getHallName())
                .templateId(req.getTemplateId())
                .startDatetime(req.getStartDatetime())
                .endDatetime(req.getEndDatetime())
                .hallStartDatetime(req.getHallStartDatetime())
                .hallEndDatetime(req.getHallEndDatetime())
                .parentSessionId(req.getParentSessionId())
                .questionYn(req.getQuestionYn())
                .mobileChatYn(req.getMobileChatYn())
                .packages(packageList)
                .stateCode(req.getStateCode())
                .onlineCode(req.getOnlineCode())
                .sessionCoverImages(sessionCoverImageList)
                .sessionMobileBottomBanners(mobileBottomBannerList)
                .onlineSpaceInfos(onlineSpaceList)
                .attachments(attachmentList)
                .sessionStaffs(sessionStaffList)
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PackageCreateInfo {
        private boolean isOnlineOnly;
        @NotNull(message = "packages is null")
        private List<PackageCreateRequest> packages;

        @Builder
        public PackageCreateInfo(boolean isOnlineOnly, List<PackageCreateRequest> packages) {
            this.isOnlineOnly = isOnlineOnly;
            this.packages = packages;
        }

        public List<PackageCreateRequest> getPackages() {
            return Objects.isNull(packages) ? null : Collections.unmodifiableList(packages);
        }
    }
}
