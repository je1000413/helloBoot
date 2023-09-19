package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDetailGetResponse {
    public String html;

    @Builder
    public EventDetailGetResponse(String html) {
        this.html = html;
    }
}
