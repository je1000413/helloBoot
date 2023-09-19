package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventCreateResponse {
    private long eventId;

    @Builder
    public EventCreateResponse(long eventId) {
        this.eventId = eventId;
    }
}
