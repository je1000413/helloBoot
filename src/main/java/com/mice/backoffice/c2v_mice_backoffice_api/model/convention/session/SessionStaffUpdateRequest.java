package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.StaffType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class SessionStaffUpdateRequest {
    private long staffId;
    @NotNull(message = "staffName is null")
    private Map<LanguageType, String> staffName;
    @NotNull(message = "domainName is null")
    private Map<LanguageType, String> domainName;
    @NotNull(message = "staffDescription is null")
    private Map<LanguageType, String> staffDescription;
    private Map<LanguageType, String> photoPath;

    private StaffType staffType;

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
        return Objects.isNull(photoPath) || photoPath.isEmpty() ? null : Collections.unmodifiableMap(photoPath);
    }
}
