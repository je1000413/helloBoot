package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.StaffType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionStaffEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SessionStaffGetResponse {
    private long staffId;
    private Map<LanguageType, String> staffName;
    private Map<LanguageType, String> domainName;
    private Map<LanguageType, String> staffDescription;
    private Map<LanguageType, String> photoPath;
    private StaffType staffType;
    private long createUserId;
    private long updateUserId;

    @Builder
    public SessionStaffGetResponse(long staffId, Map<LanguageType, String> staffName, Map<LanguageType, String> domainName, Map<LanguageType, String> staffDescription, StaffType staffType, Map<LanguageType, String> photoPath, long createUserId, long updateUserId) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.domainName = domainName;
        this.staffDescription = staffDescription;
        this.photoPath = photoPath;
        this.staffType = staffType;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }

    public static SessionStaffGetResponseBuilder dataBuilder(SessionStaffEntity sse) {
        return builder()
                .staffId(sse.getStaffId())
                .staffType(sse.getStaffType());
    }
}
