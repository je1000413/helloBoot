package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventSellStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.TicketEndType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.lounge.LoungeNameDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminInfoResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class EventGetResponse {
    private long eventId;
    private Map<LanguageType, Boolean> languageTypeMap;
    private Map<LanguageType, String> eventName;
    private Map<LanguageType, String> eventDescription;
    private List<LoungeNameDto> loungeInfos;
    private Map<LanguageType, String> offlineLocation;
    private Map<LanguageType, String> offlineDetailLocation;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private LocalDateTime sellStartDatetime;
    private LocalDateTime sellEndDatetime;
    private LocalDateTime displayStartDatetime;
    private LocalDateTime displayEndDatetime;
    private TicketEndType ticketEndType;
    private EventStateCode stateCode;
    private EventDisplayStateCode displayStateCode;
    private EventSellStateCode sellStateCode;

    private ScreenGetResponse eventDetailPage;

    private EventHostGetResponse eventHostInfo;
    private List<SurveyGetResponse> surveys;

    private List<ScreenGetResponse> eventCoverImages;
    private List<ScreenGetResponse> eventPopupBanners;

    private AdminInfoResponse createUserInfo;
    private AdminInfoResponse updateUserInfo;

    @Builder
    public EventGetResponse(long eventId, Map<LanguageType, Boolean> languageTypeMap, Map<LanguageType, String> eventName, Map<LanguageType, String> eventDescription,
                            Map<LanguageType, String> offlineLocation, Map<LanguageType, String> offlineDetailLocation, LocalDateTime startDatetime, LocalDateTime endDatetime,
                            LocalDateTime sellStartDatetime, LocalDateTime sellEndDatetime, List<LoungeNameDto> loungeInfos,
                            LocalDateTime displayStartDatetime, LocalDateTime displayEndDatetime, TicketEndType ticketEndType, EventStateCode stateCode, EventDisplayStateCode displayStateCode, EventSellStateCode sellStateCode,
                            ScreenGetResponse eventDetailPage, EventHostGetResponse eventHostInfo, List<SurveyGetResponse> surveys, List<ScreenGetResponse> eventCoverImages, List<ScreenGetResponse> eventPopupBanners,
                            AdminInfoResponse createUserInfo, AdminInfoResponse updateUserInfo) {
        this.eventId = eventId;
        this.languageTypeMap = languageTypeMap;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.offlineLocation = offlineLocation;
        this.offlineDetailLocation = offlineDetailLocation;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.sellStartDatetime = sellStartDatetime;
        this.sellEndDatetime = sellEndDatetime;
        this.displayStartDatetime = displayStartDatetime;
        this.displayEndDatetime = displayEndDatetime;
        this.ticketEndType = ticketEndType;
        this.stateCode = stateCode;
        this.displayStateCode = displayStateCode;
        this.sellStateCode = sellStateCode;
        this.eventHostInfo = eventHostInfo;
        this.surveys = surveys;
        this.eventCoverImages = eventCoverImages;
        this.eventPopupBanners = eventPopupBanners;
        this.eventDetailPage = eventDetailPage;
        this.loungeInfos = loungeInfos;
        this.createUserInfo = createUserInfo;
        this.updateUserInfo = updateUserInfo;
    }

    public EventGetResponse init() {
        eventName.putIfAbsent(LanguageType.KO_KR, "");
        eventDescription.putIfAbsent(LanguageType.KO_KR, "");
        offlineLocation.putIfAbsent(LanguageType.KO_KR, "");
        offlineDetailLocation.putIfAbsent(LanguageType.KO_KR, "");

        return this;
    }

    public static EventGetResponseBuilder dataBuilder(EventEntity ee) {
        return builder()
                .eventId(ee.getEventId())
                .startDatetime(ee.getStartDatetime())
                .endDatetime(ee.getEndDatetime())
                .sellStartDatetime(ee.getSellStartDatetime())
                .sellEndDatetime(ee.getSellEndDatetime())
                .displayStartDatetime(ee.getDisplayStartDatetime())
                .displayEndDatetime(ee.getDisplayEndDatetime())
                .ticketEndType(ee.getTicketEndType())
                .stateCode(ee.getStateCode())
                .displayStateCode(ee.getDisplayStateCode())
                .sellStateCode(ee.getSellStateCode())
                .createUserInfo(ee.getCreateUser().getUpdateUserInfo(ee.getCreateDatetime()))
                .updateUserInfo(ee.getUpdateUser().getCreateUserInfo(ee.getUpdateDatetime()));
    }
}