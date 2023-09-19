package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SessionDisplayGetResponse {
    private long displaySeq;
    private String location;
    private DisplayCode displayCode;
    private DisplayType displayType;
    private Map<LanguageType, String> displayContents;
    private long createUserId;
    private long updateUserId;
    private String fileName;
    private SessionDisplayStateCode stateCode;
    @Builder
    public SessionDisplayGetResponse(long displaySeq, String location, DisplayCode displayCode, DisplayType displayType, Map<LanguageType, String> displayContents, long createUserId, long updateUserId, String fileName, SessionDisplayStateCode stateCode) {
        this.displaySeq = displaySeq;
        this.location = location;
        this.displayCode = displayCode;
        this.displayType = displayType;
        this.displayContents = displayContents;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.fileName = fileName;
        this.stateCode = stateCode;
    }

    public static SessionDisplayGetResponseBuilder dataBuilder(SessionDisplayEntity sde) {
        return builder()
                .displaySeq(sde.getSessionDisplayId().getDisplaySeq())
                .location(sde.getLocation())
                .displayCode(sde.getDisplayCode())
                .displayType(sde.getDisplayType())
                .stateCode(sde.getStateCode())
                .createUserId(sde.getCreateUserId())
                .updateUserId(sde.getUpdateUserId());
    }
}
