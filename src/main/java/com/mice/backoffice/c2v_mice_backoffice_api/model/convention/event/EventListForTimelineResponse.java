package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;


import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class EventListForTimelineResponse {

    private long eventId;
    private Map<String, Object> eventName;
    private String eventStartDatetime;
    private String eventEndDatetime;

    private List<EventDay> eventDays;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class EventDay {
        private String eventDay;
        private int eventSort;

        private List<EventProgram> eventPrograms;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class EventProgram {
        private long ProgramId;

        private Map<String, Object> ProgramName;

        private Map<String, Object> ProgramTypeName;

        private List<EventProgramSession> eventProgramSessions;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class EventProgramSession {
        private long SessionId;
        private Map<String, Object> SessionName;
        private String staffName;
        private Map<String, Object> hallName;
        private String sessionStartTimeline;
        private String sessionEndTimeline;
        private int sessionMinDiff;
    }

}