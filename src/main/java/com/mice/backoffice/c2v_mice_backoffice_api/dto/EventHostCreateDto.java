package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventHostCreateDto {
    private long eventId;
    private long createUserId;
    private long updateUserId;

    @Builder
    public EventHostCreateDto(long eventId, long createUserId, long updateUserId) {
        this.eventId = eventId;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }

    public static EventHostCreateDtoBuilder makeEventHostCreateDto(EventEntity ee) {
        return builder()
                .eventId(ee.getEventId())
                .createUserId(ee.getCreateUserId())
                .updateUserId(ee.getUpdateUserId());
    }
}
