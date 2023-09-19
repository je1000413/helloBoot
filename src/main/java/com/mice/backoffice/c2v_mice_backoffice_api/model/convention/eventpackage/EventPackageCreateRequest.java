package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class EventPackageCreateRequest {
    private PackageType packageType;
    private Map<LanguageType, String> packageName;

    private PackageAuthorityCode packageAuthorityCode;

    private long price;
    private int maxPackageCount;

    private long eventId;
    private long programId;
    private List<Long> sessionIds;

    public Map<LanguageType, String> getPackageName() {
        return Objects.isNull(packageName) || packageName.isEmpty() ? null : Collections.unmodifiableMap(packageName);
    }

    public List<Long> getSessionIds() {
        return Objects.isNull(sessionIds) || sessionIds.isEmpty() ? null : Collections.unmodifiableList(sessionIds);
    }
}
