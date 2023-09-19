package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenGetResponse {
    private long screenId;
    private String spaceId;
    private long spaceObjectId;
    private DisplayCode displayCode;
    private long createUserId;
    private long updateUserId;
    private ScreenStateCode stateCode;

    private List<ScreenDisplayGetResponse> screenDisplays;

    @Builder
    public ScreenGetResponse(long screenId, String spaceId, long spaceObjectId, DisplayCode displayCode,
                             long createUserId, long updateUserId, ScreenStateCode stateCode, List<ScreenDisplayGetResponse> screenDisplays) {
        this.screenId = screenId;
        this.spaceId = spaceId;
        this.spaceObjectId = spaceObjectId;
        this.displayCode = displayCode;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.screenDisplays = screenDisplays;
        this.stateCode = stateCode;
    }

    public static ScreenGetResponseBuilder dataBuilder(ScreenEntity se) {
        return builder()
                .screenId(se.getScreenId())
                .spaceId(se.getSpaceId())
                .spaceObjectId(se.getSpaceObjectId())
                .displayCode(se.getDisplayCode())
                .stateCode(se.getStateCode())
                .createUserId(se.getCreateUserId())
                .updateUserId(se.getUpdateUserId());
    }
}
