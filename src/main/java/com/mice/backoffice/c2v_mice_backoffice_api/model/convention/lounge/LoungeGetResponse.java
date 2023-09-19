package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge;


import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminInfoResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class LoungeGetResponse {
    private long eventId;
    private Map<Supports.LanguageType, String> eventName;
    private int loungeNo;
    private Map<Supports.LanguageType, String> loungeName;
    private LocalDateTime loungeStartDatetime;
    private LocalDateTime loungeEndDatetime;
    private String spaceId;
    private long templateId;
    private Object templateName;
    private List<ScreenGetResponse> screenList;
    private Integer normalLoungeCnt;
    private Integer expLoungeCnt;
    private AdminInfoResponse createUserInfo;
    private AdminInfoResponse updateUserInfo;

    @Builder
    public LoungeGetResponse(
                            long eventId,
                            Map<Supports.LanguageType, String> eventName,
                            int loungeNo,
                            Map<Supports.LanguageType, String> loungeName,
                            LocalDateTime loungeStartDatetime,
                            LocalDateTime loungeEndDatetime,
                            String spaceId,
                            long templateId,
                            List<ScreenGetResponse> screenList,
                            Integer normalLoungeCnt,
                            Integer expLoungeCnt,
                            Object templateName,
                            AdminInfoResponse createUserInfo,
                            AdminInfoResponse updateUserInfo) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.loungeNo = loungeNo;
        this.loungeName = loungeName;
        this.loungeStartDatetime = loungeStartDatetime;
        this.loungeEndDatetime = loungeEndDatetime;
        this.spaceId = spaceId;
        this.templateId = templateId;
        this.screenList = screenList;
        this.normalLoungeCnt = normalLoungeCnt;
        this.expLoungeCnt = expLoungeCnt;
        this.templateName = templateName;
        this.createUserInfo = createUserInfo;
        this.updateUserInfo = updateUserInfo;
    }

    public static LoungeGetResponseBuilder dataBuilder(LoungeEntity le, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return builder()
                .eventId(le.getPk().getEventId())
                .loungeNo(le.getPk().getLoungeNo())
                .loungeStartDatetime(startDatetime)
                .loungeEndDatetime(endDatetime)
                .spaceId(le.getSpaceId())
                .templateId(le.getTemplateId())
                .createUserInfo(le.getCreateUser().getUpdateUserInfo(le.getCreateDatetime()))
                .updateUserInfo(le.getUpdateUser().getUpdateUserInfo(le.getUpdateDatetime()));
    }
}

