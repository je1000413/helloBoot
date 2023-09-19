package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.PriceType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.TicketEndType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;

@Getter
@NoArgsConstructor
public class EventUpdateRequest {
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

    public ScreenUpdateRequest eventDetailPage;

    @NotNull(message = "eventHostReq is null")
    private List<EventHostUpdateRequest> eventHosts;
    private List<SurveyUpdateRequest> surveys;

    @NotNull(message = "eventCoverImage is null")
    public List<ScreenUpdateRequest> eventCoverImages;
    public List<ScreenUpdateRequest> eventPopupBanners;

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

    public List<EventHostUpdateRequest> getEventHosts() {
        return Objects.isNull(eventHosts) || eventHosts.isEmpty() ? null : Collections.unmodifiableList(eventHosts);
    }

    public List<SurveyUpdateRequest> getSurveys() {
        return Objects.isNull(surveys) || surveys.isEmpty() ? null : Collections.unmodifiableList(surveys);
    }

    public List<ScreenUpdateRequest> getEventCoverImages() {
        return Objects.isNull(eventCoverImages) || eventCoverImages.isEmpty() ? null : Collections.unmodifiableList(eventCoverImages);
    }

    public List<ScreenUpdateRequest> getEventPopupBanners() {
        return Objects.isNull(eventPopupBanners) || eventPopupBanners.isEmpty() ? null : Collections.unmodifiableList(eventPopupBanners);
    }
}
