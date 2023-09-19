package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class EventPackageGetResponse {
    private String packageId;
    private Map<LanguageType, String> packageName;
    private PackageType packageType;
    private PackageAuthorityCode packageAuthorityCode;
    private PackageStateCode stateCode;
    private long price;
    private int maxPackageCount;

    // 행사 정보
    private Map<LanguageType, String> eventName;
    private long eventId;

    // 프로그램 정보
    private String programNames;
    private Set<ProgramGetResponse> programs;

    // 세션 정보
    private String sessionNames;

    @Builder
    public EventPackageGetResponse(String packageId, Map<LanguageType, String> packageName, PackageType packageType, PackageAuthorityCode packageAuthorityCode,
                                   PackageStateCode stateCode, long price, int maxPackageCount, Map<LanguageType, String> eventName, long eventId, String programNames, Set<ProgramGetResponse> programs,
                                   String sessionNames) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packageType = packageType;
        this.packageAuthorityCode = packageAuthorityCode;
        this.stateCode = stateCode;
        this.price = price;
        this.maxPackageCount = maxPackageCount;
        this.eventName = eventName;
        this.eventId = eventId;
        this.programNames = programNames;
        this.programs = programs;
        this.sessionNames = sessionNames;
    }

    public static EventPackageGetResponseBuilder dataBuilder(PackageEntity pe) {
        return builder()
                .packageId(pe.getPackageId())
                .packageType(pe.getPackageType())
                .eventId(pe.getEventId())
                .packageAuthorityCode(pe.getPackageAuthorityCode())
                .stateCode(pe.getStateCode())
                .price(pe.getPrice())
                .maxPackageCount(pe.getMaxPackageCount());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProgramGetResponse {
        private long programId;
        private Map<LanguageType, String> programName;

        // 세션 정보
        private Set<SessionGetResponse> sessions;

        @Builder
        public ProgramGetResponse(long programId, Map<LanguageType, String> programName, Set<SessionGetResponse> sessions) {
            this.programId = programId;
            this.programName = programName;
            this.sessions = sessions;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SessionGetResponse {
        private long sessionId;
        private Map<LanguageType, String> sessionName;

        @Builder
        public SessionGetResponse(long sessionId, Map<LanguageType, String> sessionName) {
            this.sessionId = sessionId;
            this.sessionName = sessionName;
        }

        public static SessionGetResponse makeGetResponse(long sessionId, Map<LanguageType, String> sessionName) {
            return builder()
                    .sessionId(sessionId)
                    .sessionName(sessionName)
                    .build();
        }
    }
}
