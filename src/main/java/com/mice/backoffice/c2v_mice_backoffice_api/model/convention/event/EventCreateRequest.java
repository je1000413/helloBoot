package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.*;

@Getter
@NoArgsConstructor
public class EventCreateRequest {
    private long eventId;
    @NotNull(message = "eventName is null")
    private Map<LanguageType, String> eventName;
    @NotNull(message = "eventDescription is null")
    private Map<LanguageType, String> eventDescription;
    @NotNull(message = "offlineLocation is null")
    private Map<LanguageType, String> offlineLocation;
    @NotNull(message = "offlineDetailLocation is null")
    private Map<LanguageType, String> offlineDetailLocation;
    @NotNull(message = "startDatetime is null")
    private LocalDateTime startDatetime;
    @NotNull(message = "endDatetime is null")
    private LocalDateTime endDatetime;
    @NotNull(message = "sellStartDatetime is null")
    private LocalDateTime sellStartDatetime;
    @NotNull(message = "sellEndDatetime is null")
    private LocalDateTime sellEndDatetime;
    @NotNull(message = "displayStartDatetime is null")
    private LocalDateTime displayStartDatetime;
    @NotNull(message = "displayEndDatetime is null")
    private LocalDateTime displayEndDatetime;
    private TicketEndType ticketEndType;

    @NotNull(message = "eventDetailPage is null")
    private ScreenCreateRequest eventDetailPage;

    @NotNull(message = "eventHosts is null")
    private List<EventHostCreateRequest> eventHosts;
    private List<SurveyCreateRequest> surveys;

    @NotNull(message = "eventCoverImage is null")
    private List<ScreenCreateRequest> eventCoverImages;
    private List<ScreenCreateRequest> eventPopupBanners;

    @Builder
    public EventCreateRequest(Map<LanguageType, String> eventName, Map<LanguageType, String> eventDescription,
                              Map<LanguageType, String> offlineLocation, Map<LanguageType, String> offlineDetailLocation, LocalDateTime startDatetime, LocalDateTime endDatetime,
                              LocalDateTime sellStartDatetime, LocalDateTime sellEndDatetime, LocalDateTime displayStartDatetime, LocalDateTime displayEndDatetime, TicketEndType ticketEndType,
                              List<EventHostCreateRequest> eventHosts, List<SurveyCreateRequest> surveys, ScreenCreateRequest eventDetailPage,
                              List<ScreenCreateRequest> eventCoverImages, List<ScreenCreateRequest> eventPopupBanners) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.offlineLocation = offlineLocation;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.sellStartDatetime = sellStartDatetime;
        this.sellEndDatetime = sellEndDatetime;
        this.displayStartDatetime = displayStartDatetime;
        this.displayEndDatetime = displayEndDatetime;
        this.ticketEndType = ticketEndType;
        this.eventHosts = eventHosts;
        this.surveys = surveys;
        this.eventCoverImages = eventCoverImages;
        this.eventPopupBanners = eventPopupBanners;
        this.eventDetailPage = eventDetailPage;
        this.offlineDetailLocation = offlineDetailLocation;
    }

    public Map<LanguageType, String> getEventName() {
        return Objects.isNull(eventName) || eventName.isEmpty() ? null : Collections.unmodifiableMap(eventName);
    }

    public Map<LanguageType, String> getEventDescription() {
        return Objects.isNull(eventDescription) || eventDescription.isEmpty() ? null : Collections.unmodifiableMap(eventDescription);
    }

    public Map<LanguageType, String> getOfflineLocation() {
        return Objects.isNull(offlineLocation) || offlineLocation.isEmpty() ? null : Collections.unmodifiableMap(offlineLocation);
    }

    public Map<LanguageType, String> getOfflineDetailLocation() {
        return Objects.isNull(offlineDetailLocation) || offlineDetailLocation.isEmpty() ? null : Collections.unmodifiableMap(offlineDetailLocation);
    }

    public List<EventHostCreateRequest> getEventHosts() {
        return Objects.isNull(eventHosts) || eventHosts.isEmpty() ? null : Collections.unmodifiableList(eventHosts);
    }

    public List<SurveyCreateRequest> getSurveys() {
        return Objects.isNull(surveys) || surveys.isEmpty() ? null : Collections.unmodifiableList(surveys);
    }

    public List<ScreenCreateRequest> getEventCoverImages() {
        return Objects.isNull(eventCoverImages) || eventCoverImages.isEmpty() ? null : Collections.unmodifiableList(eventCoverImages);
    }

    public List<ScreenCreateRequest> getEventPopupBanners() {
        return Objects.isNull(eventPopupBanners) || eventPopupBanners.isEmpty() ? null : Collections.unmodifiableList(eventPopupBanners);
    }
}
