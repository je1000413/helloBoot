package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenDisplayEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ScreenDisplayGetResponse {
    private int displaySeq;
    private int pageNo;
    private char soundYn;
    private ScreenDisplayStateCode stateCode;
    private DisplayType displayType;
    private Map<LanguageType, String> displayContents;
    private Map<LanguageType, String> linkAddress;
    private Map<LanguageType, String> fileName;
    private long createUserId;
    private long updateUserId;

    @Builder
    public ScreenDisplayGetResponse(int displaySeq, int pageNo, DisplayType displayType,
                                    char soundYn, ScreenDisplayStateCode stateCode,
                                    Map<LanguageType, String> displayContents, Map<LanguageType, String> fileName, long createUserId, long updateUserId,
                                    Map<LanguageType, String> linkAddress) {
        this.displaySeq = displaySeq;
        this.pageNo = pageNo;
        this.displayType = displayType;
        this.soundYn = soundYn;
        this.stateCode = stateCode;
        this.fileName = fileName;
        this.displayContents = displayContents;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.linkAddress = linkAddress;
//        this.fileName = fileName(displayContents);
    }

    public static ScreenDisplayGetResponseBuilder dataBuilder(ScreenDisplayEntity sde) {
        return builder()
                .displaySeq(sde.getDisplaySeq())
                .pageNo(sde.getPageNo())
                .displayType(sde.getDisplayType())
                .soundYn(sde.getSoundYn())
                .stateCode(sde.getStateCode())
                .createUserId(sde.getCreateUserId())
                .updateUserId(sde.getUpdateUserId());
    }
}
