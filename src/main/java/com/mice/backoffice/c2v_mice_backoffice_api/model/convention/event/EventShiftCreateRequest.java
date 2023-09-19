package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.PriceType;
import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.TicketEndType;

@Getter
@NoArgsConstructor
public class EventShiftCreateRequest {
    private long eventId;
    @NotNull(message = "eventName is null")
    private Map<LanguageType, String> eventName;
    private Map<LanguageType, String> eventDescription;
    private Map<LanguageType, String> offlineLocation;
    private Map<LanguageType, String> offlineDetailLocation;
    @NotNull(message = "startDatetime is null")
    private LocalDateTime startDatetime;
    @NotNull(message = "endDatetime is null")
    private LocalDateTime endDatetime;
    private LocalDateTime sellStartDatetime;
    private LocalDateTime sellEndDatetime;
    private LocalDateTime displayStartDatetime;
    private LocalDateTime displayEndDatetime;
    private TicketEndType ticketEndType;

    private ScreenCreateRequest eventDetailPage;

    @NotNull(message = "eventHosts is null")
    private List<EventHostCreateRequest> eventHosts;
    private List<SurveyCreateRequest> surveys;

    private List<ScreenCreateRequest> eventCoverImages;
    private List<ScreenCreateRequest> eventPopupBanners;

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
