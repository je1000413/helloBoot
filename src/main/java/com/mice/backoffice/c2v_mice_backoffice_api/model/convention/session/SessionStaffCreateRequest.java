package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.StaffType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionStaffCreateRequest {
    @NotNull(message = "staffName is null")
    private Map<LanguageType, String> staffName;
    @NotNull(message = "domainName is null")
    private Map<LanguageType, String> domainName;
    @NotNull(message = "staffDescription is null")
    private Map<LanguageType, String> staffDescription;
    private Map<LanguageType, String> photoPath;
    private StaffType staffType;

    @Builder
    public SessionStaffCreateRequest(Map<LanguageType, String> staffName, Map<LanguageType, String> domainName, Map<LanguageType, String> staffDescription, Map<LanguageType, String> photoPath, StaffType staffType) {
        this.staffName = staffName;
        this.domainName = domainName;
        this.staffDescription = staffDescription;
        this.photoPath = photoPath;
        this.staffType = staffType;
    }

    public Map<LanguageType, String> getStaffName() {
        return Objects.isNull(staffName) || staffName.isEmpty() ? null : Collections.unmodifiableMap(staffName);
    }

    public Map<LanguageType, String> getDomainName() {
        return Objects.isNull(domainName) || domainName.isEmpty() ? null : Collections.unmodifiableMap(domainName);
    }

    public Map<LanguageType, String> getStaffDescription() {
        return Objects.isNull(staffDescription) || staffDescription.isEmpty() ? null : Collections.unmodifiableMap(staffDescription);
    }

    public Map<LanguageType, String> getPhotoPath() {
        return Objects.isNull(photoPath) || photoPath.isEmpty() ? new HashMap<>() : Collections.unmodifiableMap(photoPath);
    }

    public static SessionStaffCreateRequest makeCreateRequest(SessionStaffUpdateRequest req) {
        return builder()
                .staffType(req.getStaffType())
                .staffName(req.getStaffName())
                .domainName(req.getDomainName())
                .staffDescription(req.getStaffDescription())
                .photoPath(req.getPhotoPath())
                .build();
    }
}
